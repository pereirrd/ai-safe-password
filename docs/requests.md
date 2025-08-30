Validate with regular expression:
```
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd!"
}'
```

Validate with AI:
```
curl --location 'http://localhost:8080/ai/validate' \
--header 'Content-Type: application/json' \
--data '{
    "password": "nv77678Klsd2222"
}'
```

Generate with AI:
```
curl --location 'http://localhost:8080/ai/generate'
```