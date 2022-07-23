# /login



---

**Method** : `GET`
**역할** : `html 페이지`

## Success Response
**상태 코드** : `200 OK`
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`

---


**Method** : `POST`
**역할** : `유저 로그인`

**Form Parameter**
``` 
id : String
password : String

```


## Success Response
**Rediect Url** : `/`
**상태 코드** : `301 Redirect`
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`
