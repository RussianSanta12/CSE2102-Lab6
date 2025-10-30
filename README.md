# CSE2102-Lab6
in this lab i started by unzipping the demo.zip file to get the make spring files. First thing I noticed when trying to run the code was the the wrapper file was never created so i ran the code mvn wrapper:wrapper and then asked copilot what would be needed within that file. after that i instructed co pilot to generate the code for a spring java system that uses rest endpoints to check the quality of passwords and validity of emails i ran the lines: curl -s -X POST -H "Content-Type: application/json" -d '{"password":"S3cure!Passw0rd#2025"}' http://localhost:8080/api/password/quality | jq curl -s -X POST -H "Content-Type: application/json" -d '{"email":"user@example.com"}' http://localhost:8080/api/email/validate | jq
both lines were generated for me and gave the output
password : S3cure!Passw0rd#2025 | strong score:100
email : user@example.com | True "looks Valid"

to run the system the lines ./mvnw package and ./mvnw spring-boot:run
