# /post/delete


**Url Parameter**
``` typescript
{
    "postUUID" : String
}
```
## Example
```
/post/delete/:postUUID
```
---
**Method** : `DELETE`
**역할** : `포스트 삭제.`

## Success Response
**상태 코드** : `200 OK`
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`
- `403 Forbidden`



