# /user


**Method** : `GET`
**역할** : `유저의 정보를 불려온다`

**Url Parameter**
``` typescript
{
    "userUUID" : String
}
```

## Example
```
/user/:userUUID
```

## Success Response
**상태 코드** : `200 OK`
``` typescript
{
    "name" : String
    "imageData" : String
}
```
**에러 코드** : `404 Not Found`

---

**Method** : `PATCH`
**necessary session**  
**역할** : `유저의 비밀번호, 이름등 여러 정보를 수정 한다`
**Request Body**
``` typescript
{
    "name" : String | undefined
    "imageData" : String | undefined
    "password" : String | undefined
}
```

## Example
``` typescript
{
    "userUUID" : "uuid"
    "name" : "sunken ahn"
    "imageData" : "data:image/png,...."
}
```

## Success Response
**상태 코드** : `200 OK`
## Error Response
**에러 코드**
- `403 Forbidden`
- `400 Bad Request`


---

**Method** : `DELETE`
**necessary session**  
**역할** : `유저의 회원탈퇴 기능을 수행한다.`


## Success Response
**상태 코드** : `204 No Content`
## Error Response
**에러 코드**
- `403 Forbidden`
- `400 Bad Request`


