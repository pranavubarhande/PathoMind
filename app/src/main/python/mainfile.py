import base64
import requests

def code(j):
    convertedstring = base64.b64encode(j)
    cts = convertedstring.decode()
    weburl = "http://192.168.43.49:5000/give"
    # PARAMS = {"namestring":cts}
    # r = requests.get(weburl, params=PARAMS)
    # res = requests.post('http://192.168.43.49:5000/give', json={"phototobesend":cts})
    # sj = base64.b64decode((res.text.strip()))
    return cts

