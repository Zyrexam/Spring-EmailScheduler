#  📧 Spring-EmailScheduler

A Spring Boot application that schedules and sends emails using Quartz Scheduler with professional email templates.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)

A Spring Boot application that schedules and sends emails using Quartz Scheduler with professional email templates.

## 📸 Screenshots

### API Endpoints
<img src="src/main/resources/static/img1.png" width="600" alt="RestApi-EndPoints"/>

### Email Result
<img src="src/main/resources/static/img2.jpg" width="300" alt="Email Sent-Received result"/>

## ✨ Features

- Schedule emails with custom templates
- Support for company and personal information in emails
- Timezone-aware scheduling
- Uses Quartz for reliable email scheduling
- RESTful API endpoints

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Quartz Scheduler
- Spring Mail
- Maven


## ⚙️ Configuration

Add the following properties to `application.properties`:

```properties
# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true




## 🚀 Running the Application

1. Clone the repository
```bash
git clone https://github.com/Zyrexam/Spring-EmailScheduler.git
```

2. Configure email settings in `application.properties`

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```


## 🔒 Security

- Uses app-specific passwords for Gmail authentication
- Validates scheduled time to ensure it's in the future
- Implements proper error handling and logging
