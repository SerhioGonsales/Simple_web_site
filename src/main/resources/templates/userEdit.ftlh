<#import "parts/common.ftlh" as c>

<@c.page>
Редактор пользователя
    <form action="/user" method="post">
        <input type="text" name="username" value="${user.userName}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}"${user.role?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">сохранить изменения</button>
    </form>
</@c.page>