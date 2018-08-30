# SoundSystem
Sound system for CR. Supports text to speech and jingle play based on system commands 'say' and 'afplay'

# Directory scanning
In application.properties file the property jingle.path point to the directory with jingles files. This directory will be scanned on application startup.

#API
Following endpoints are available:
* /say - send POST request to schedule text to speach, body of the post will be spoken
* /play - send POST request to schedule jingle play, accepts filename to play (handles filenames with and without extension)
* /records lists - send GET request to get list of sound records (include request time, start of execution, end of execution, body of request, and response body)

#Examples

Request: POST localhost:8080/say with body hello
Response: Scheduled to say: dataflow stuck

Request: POST localhost:8080/play with body example.wmv
Response: Scheduled to play jingle: example.wmv

Request: GET localhost:8080/records
Response:
[
    {
        "requestType": "say",
        "requestBody": "dataflow stuck",
        "called": "2018-08-30T16:14:04.399+0000",
        "executionStarted": "2018-08-30T16:14:04.441+0000",
        "executionFinished": "2018-08-30T16:14:05.632+0000",
        "response": "Scheduled to say: dataflow stuck"
    },
    {
        "requestType": "play",
        "requestBody": "sad",
        "called": "2018-08-30T16:14:39.399+0000",
        "executionStarted": "2018-08-30T16:14:39.400+0000",
        "executionFinished": "2018-08-30T16:14:47.875+0000",
        "response": "Scheduled to play jingle: sad"
    }
]
