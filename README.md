# Forget-password microservice

Small Spring Boot service that **creates, emails (or logs), and verifies** a 6-digit OTP for password reset.

It does **not** store users or passwords. The marketplace owns that:

**[marketplace-backend](https://github.com/basmalaabuhakmeh-hub/marketplace-backend)**

The marketplace calls this app with HTTP (`RestTemplate`):

- `POST /otp/send` `{ "email": "..." }`
- `POST /otp/verify` `{ "email": "...", "otp": "123456" }`

OTP is valid for **10 minutes** and can be used **once**.

## Run

- Java 17, Maven, MySQL
- Port **8081** (marketplace is 8080)
- Database `forget_password` is created automatically if it does not exist (`application.properties`)

Start **this app first**, then the marketplace.

If `resend.api-key` is empty, the OTP is printed in the console (`Resend not configured. OTP for ... = 123456`). To send real email, paste a [Resend API key](https://resend.com) into `application.properties`. Until you verify a domain, Resend only delivers to the email you signed up with (`from` can stay `onboarding@resend.dev`).

## Test this service alone (optional)

```
POST http://localhost:8081/otp/send
{ "email": "anyone@mail.com" }

POST http://localhost:8081/otp/verify
{ "email": "anyone@mail.com", "otp": "paste-from-console" }
```

For a full reset (new password in the shop DB), use the marketplace `/auth/forgot-password` and `/auth/reset-password` instead. Do not verify here first — that uses up the OTP.
