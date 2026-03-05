# Spring Boot Upgrade: 3.5.7 to 4.0.0

## Executive Summary
**Upgrade Status**: SUCCESSFUL

### Key Metrics
- **Files Modified**: 12 (pom.xml, JpaOwnerRepositoryImpl.java, plus OpenRewrite changes to 9 files, and this doc)
- **Tests Status**: 216/216 passing
- **Build Status**: SUCCESS
- **JaCoCo Coverage**: All checks met

### Major Changes
1. Upgraded Spring Boot parent from 3.5.7 to 4.0.0 (Spring Framework 7.0.1)
2. Replaced `spring-boot-starter-aop` with `spring-boot-starter-aspectj` (renamed in 4.0.0)
3. Upgraded `springdoc-openapi` from 2.8.13 to 3.0.2 (required for Spring Boot 4.0 compatibility)
4. Removed Hibernate 5 import (removed in Spring Framework 7.0)
5. Migrated `javax.xml.bind:jaxb-api` to `jakarta.xml.bind:jakarta.xml.bind-api` (via OpenRewrite)

### Breaking Changes Handled
- `spring-boot-starter-aop` removed; replaced with `spring-boot-starter-aspectj`
- `org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties` relocated; fixed by upgrading springdoc to 3.0.2
- `org.springframework.orm.hibernate5.support` package removed; removed unused import
- `javax.xml.bind` to `jakarta.xml.bind` namespace migration (via OpenRewrite)
- Deprecated `security.ignored` property commented out (via OpenRewrite)

### Known Issues
- None

### Recommendations
- Consider upgrading to Java 21 LTS for better performance with Spring Boot 4.0.0
- Monitor springdoc-openapi 3.0.x for newer releases

## Upgrade Details
- **Current Version**: 3.5.7
- **Target Version**: 4.0.0
- **Upgrade Date**: 2026-03-05
- **Upgrade Type**: Major (Spring Boot 3.x to 4.x, Spring Framework 6.x to 7.x)
- **Project**: spring-petclinic-rest (AI-Data-Ranch/spring-petclinic-rest)

## Compatibility Analysis
- **Java Version**: 17 (OpenJDK 17.0.13)
- **Spring Boot 4.0.0 Compatibility**: Requires Java 17+; current Java 17 is compatible
- **Spring Framework**: 6.2.12 to 7.0.1

## Pre-Upgrade State
### Test Status Before Upgrade
- Build Status: PASS
- Total Tests: 216
- Passing Tests: 216
- JaCoCo Coverage: All checks met

## OpenRewrite Migration Results
### Recipe Executed
- **Recipe**: `org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_4`
- **Status**: SUCCESS

### Files Modified by OpenRewrite
- `pom.xml` - javax.xml.bind to jakarta.xml.bind migration, springdoc version bump
- 7 JDBC repository implementations - Added `@DependsOnDatabaseInitialization`
- `BindingErrorsResponse.java` - `String.format()` to `.formatted()`
- `UserRestControllerTests.java` - Added `@ExtendWith(MockitoExtension.class)`, replaced `@ContextConfiguration` with `@SpringJUnitConfig`
- `src/test/resources/application.properties` - Commented out deprecated `security.ignored` property

### Manual Changes for Spring Boot 4.0
1. **Spring Boot parent version**: 3.5.7 to 4.0.0
2. **spring-boot-starter-aop to spring-boot-starter-aspectj**: Renamed in 4.0.0
3. **springdoc-openapi**: 2.8.16 to 3.0.2 (Spring Boot 4.0 compatible)
4. **Removed hibernate5 import**: No longer exists in Spring Framework 7.0

## Build Issues Encountered and Resolved

### Issue 1: Missing spring-boot-starter-aop
- **Error**: `dependencies.dependency.version for spring-boot-starter-aop:jar is missing`
- **Cause**: Removed in Spring Boot 4.0.0
- **Fix**: Replaced with `spring-boot-starter-aspectj`

### Issue 2: Missing hibernate5.support package
- **Error**: `package org.springframework.orm.hibernate5.support does not exist`
- **Cause**: Spring Framework 7.0 dropped Hibernate 5 support
- **Fix**: Removed unused import

### Issue 3: springdoc-openapi incompatibility
- **Error**: `ClassNotFoundException: org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties`
- **Cause**: springdoc 2.x incompatible with Spring Boot 4.0
- **Fix**: Upgraded to springdoc-openapi 3.0.2

## Dependency Updates
| Dependency | Old Version | New Version | Reason |
|------------|-------------|-------------|---------|
| spring-boot-starter-parent | 3.5.7 | 4.0.0 | Target upgrade |
| spring-boot-starter-aop | managed | Replaced by starter-aspectj | Removed in 4.0 |
| springdoc-openapi | 2.8.13 | 3.0.2 | Spring Boot 4.0 compatibility |
| javax.xml.bind:jaxb-api | 2.3.1 | jakarta.xml.bind-api (managed) | Jakarta EE migration |

## Test Results After Upgrade
- Total Tests: 216, Passing: 216, Failing: 0
- JaCoCo: All coverage checks met

## Key Version Alignments
- Spring Boot 4.0.0 -> Spring Framework 7.0.1
- Spring Security 7.0.0
- Jakarta EE 11

---
*Document maintained by upgrade-springboot skill - Last updated: 2026-03-05*
