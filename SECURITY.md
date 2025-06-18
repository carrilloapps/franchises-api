# Security Policy

## 🔒 Security Overview

This document outlines the security considerations, current implementation status, and recommendations for the Franchises API project.

## ⚠️ Current Security Status

### Authentication & Authorization

**❌ NO AUTHENTICATION IMPLEMENTED**

This API currently **does not implement any authentication or authorization mechanisms**. All endpoints are publicly accessible without any form of user verification.

**Implications:**
- Any client can access all API endpoints
- No user identity verification
- No role-based access control
- No API key or token validation
- All CRUD operations are unrestricted

**Recommendation for Production:**
```kotlin
// Example: Add Spring Security dependency
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
```

### Data Validation

**✅ BASIC VALIDATION IMPLEMENTED**

- Input validation using Spring Boot validation annotations
- Request body validation for required fields
- Data type validation (String, Int, etc.)
- MongoDB schema validation through domain models

### CORS Configuration

**✅ CORS ENABLED**

```kotlin
// Current CORS configuration allows all origins
@CrossOrigin(origins = ["*"])
```

**Security Concern:** Wildcard CORS policy allows requests from any domain.

**Recommendation:**
```kotlin
// Restrict to specific domains in production
@CrossOrigin(origins = ["https://yourdomain.com", "https://app.yourdomain.com"])
```

### Database Security

**⚠️ BASIC MONGODB SETUP**

- No MongoDB authentication configured
- Default connection without credentials
- No encryption at rest configured
- No connection encryption (TLS/SSL)

**Current Configuration:**
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/franchisesdb
```

**Recommended Production Configuration:**
```properties
spring.data.mongodb.uri=mongodb://username:password@localhost:27017/franchisesdb?ssl=true&authSource=admin
```

### Error Handling

**✅ SECURE ERROR RESPONSES**

- No sensitive information exposed in error messages
- Standardized error response format
- Proper HTTP status codes
- No stack traces exposed to clients

### Dependencies Security

**✅ AUTOMATED SECURITY SCANNING**

- GitHub Actions workflow includes security vulnerability scanning
- Automated dependency vulnerability detection
- Regular security updates through CI/CD pipeline

```yaml
# .github/workflows/ci.yml
- name: Run security scan
  run: ./gradlew dependencyCheckAnalyze
```

## 🛡️ Security Recommendations

### Immediate Actions for Production

1. **Implement Authentication**
   ```kotlin
   // Add JWT or OAuth2 authentication
   @EnableWebFluxSecurity
   class SecurityConfig {
       @Bean
       fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
           return http
               .authorizeExchange { exchanges ->
                   exchanges
                       .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                       .anyExchange().authenticated()
               }
               .oauth2ResourceServer { oauth2 -> oauth2.jwt() }
               .build()
       }
   }
   ```

2. **Secure MongoDB Connection**
   ```properties
   # Use environment variables for credentials
   spring.data.mongodb.uri=${MONGODB_URI}
   spring.data.mongodb.username=${MONGODB_USERNAME}
   spring.data.mongodb.password=${MONGODB_PASSWORD}
   ```

3. **Restrict CORS Origins**
   ```kotlin
   @CrossOrigin(origins = ["\${app.cors.allowed-origins}"])
   ```

4. **Add Rate Limiting**
   ```kotlin
   // Implement rate limiting to prevent abuse
   @Component
   class RateLimitingFilter : WebFilter {
       // Rate limiting implementation
   }
   ```

5. **Input Sanitization**
   ```kotlin
   // Add comprehensive input validation
   data class CreateFranchiseRequest(
       @field:NotBlank(message = "Name is required")
       @field:Size(max = 100, message = "Name must not exceed 100 characters")
       @field:Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name contains invalid characters")
       val name: String
   )
   ```

### Environment-Specific Security

#### Development Environment
- ✅ Current configuration is acceptable
- ✅ Open access for testing purposes
- ✅ Local MongoDB without authentication

#### Production Environment
- ❌ **REQUIRES IMMEDIATE SECURITY IMPLEMENTATION**
- 🔒 Authentication and authorization mandatory
- 🔒 Encrypted database connections required
- 🔒 Restricted CORS origins
- 🔒 API rate limiting
- 🔒 Security headers implementation

### Security Headers

**Recommended Security Headers:**
```kotlin
@Configuration
class SecurityHeadersConfig {
    @Bean
    fun securityHeaders(): WebFilter {
        return WebFilter { exchange, chain ->
            exchange.response.headers.apply {
                set("X-Content-Type-Options", "nosniff")
                set("X-Frame-Options", "DENY")
                set("X-XSS-Protection", "1; mode=block")
                set("Strict-Transport-Security", "max-age=31536000; includeSubDomains")
                set("Content-Security-Policy", "default-src 'self'")
            }
            chain.filter(exchange)
        }
    }
}
```

## 📋 Security Checklist

### Before Production Deployment

- [ ] Implement authentication (JWT/OAuth2)
- [ ] Add authorization/role-based access control
- [ ] Configure secure MongoDB connection with credentials
- [ ] Restrict CORS to specific domains
- [ ] Add rate limiting
- [ ] Implement security headers
- [ ] Enable HTTPS/TLS
- [ ] Configure environment-specific secrets
- [ ] Set up monitoring and logging
- [ ] Perform security penetration testing

### Ongoing Security Practices

- [x] Automated dependency vulnerability scanning
- [x] Regular security updates through CI/CD
- [ ] Security code reviews
- [ ] Regular security audits
- [ ] Monitoring and alerting for security events

## 🚨 Reporting Security Vulnerabilities

If you discover a security vulnerability in this project, please report it by:

1. **DO NOT** create a public GitHub issue
2. Send an email to: [security@carrilloapps.com](mailto:security@carrilloapps.com)
3. Include detailed information about the vulnerability
4. Provide steps to reproduce if possible

We will respond to security reports within 48 hours and provide regular updates on the resolution progress.

## 📚 Security Resources

- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [MongoDB Security Checklist](https://docs.mongodb.com/manual/administration/security-checklist/)
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [Spring Boot Security Best Practices](https://spring.io/guides/topicals/spring-security-architecture/)

---

**⚠️ IMPORTANT DISCLAIMER:** This API is currently in development and **NOT SUITABLE FOR PRODUCTION USE** without implementing the security measures outlined in this document.