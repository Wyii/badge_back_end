# API
######1. 获取当前user  
api：/users/currentuser  
参数说明：code，微信服务器返回的code

######2. 获取所有user  
 api：/users

######3. 获取用户对应的所有badge  
api：/users/{userId}  
参数说明：userId及企业号后台添加的userId

######4. 获取所有被badged过的用户  
api:/users/badged

######5. 新增一条记录  
api：/records/add  
方式：POST  
参数列表：  
toUser ： 当前badge是送给谁的，值为userId，必须  
fromUser ： 当前badge是谁送出的，值为当前userId，必须  
badge ： badge对应的Id，必须  
comment ： 评论，可选  


######6. 获取所有badge  
api：/badges
