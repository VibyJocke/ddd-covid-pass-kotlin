# ddd-covid-pass-kotlin
Example project illustrating how a DDD implementation of a covid vaccination passport system could look like.

## API

### Register a new patient
`curl -XPOST -i localhost:8080/covid-pass/patient/register -H "Content-type: application/json" -d '{"patientNin":"xxxxxxxx-xxxx"}'`
