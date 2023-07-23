<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>${message!}</div>
    <@l.login "/login" "username" false/>
</@c.page>