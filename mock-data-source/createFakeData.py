import time
import json
import random
import requests

url = "http://localhost:8080/data"
session = requests.Session()
session.headers = {
    'Content-Type': 'application/json'
}
i = 0
while True:
    data = {
        "id": i,
        "description": "",
        "completed": random.choice([True, False])
    }
    response = session.post(url, json=data)
    if not response.ok:
        raise RuntimeError(response.text)
    time.sleep(1)
    i += 1
    print(json.dumps(data))
