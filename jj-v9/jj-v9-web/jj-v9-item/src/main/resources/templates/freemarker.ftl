<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello,${username}
    <hr/>
    name:${product.name}
    price:${product.price}
    createTime:${product.createTime?date}
    createTime:${product.createTime?time}
    createTime:${product.createTime?datetime}
    <hr/>
    msg:${msg!}
    msg:${msg!"暂无消息"}
    <#if msg??>
        when-present
        <#else>
        when-missing
    </#if>
    <hr/>
    <h3>展示集合</h3>
    <#list list as product>
        name:${product.name}
        price:${product.price}
        createTime:${product.createTime?date}
        <hr/>
    </#list>
    <h3>条件展示</h3>
    <#if (money>100000)>
        混的可以
    <#else>
        垃圾
    </#if>
</body>
</html>