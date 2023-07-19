<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    Добавить нового пользователя
<#--    ${message}-->
    <@l.login "/registration"/>
</@c.page>