<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="mb-3">Добавить нового пользователя</div>
    <div>${message!}</div>
    <@l.login "/registration" "userName" true/>
</@c.page>