# /post/list


**Method** : `GET`
**역할** : `포스트 목록을 불려온다.`

**Query String**
``` typescript
{
    "start" : number
    "end" : number
}
```

## Example
```
/post/list?start=0&end=10
```

## Success Response
**상태 코드** : `200 OK`
``` typescript
[
    {
        "authorName" : String
        "postImage" : String | undefined
        "createDate" : String
        "title" : String
    },
    ...
]
```
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`



