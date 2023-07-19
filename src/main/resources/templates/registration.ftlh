<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Добавить нового пользователя
    <div>${message!}</div>
    <@l.login "/registration" "userName"/>
</@c.page>