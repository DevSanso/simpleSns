# /post/add


**Method** : `POST`
**necessary session**  
**역할** : `포스트를 추가한다.`

**Request Body**
``` typescript
{
    "content" : String
    "postImage" : String | undefined
    "title" : String
}
```

## Success Response
**상태 코드** : `200 OK`  
**Response Body**
``` typescript
{
    "postUUID" : String
}
```
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`



