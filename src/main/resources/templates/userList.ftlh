<#import "parts/common.ftlh" as c>

<@c.page>
    Список пользователей
    <table>
        <thead>
            <tr>
                <th>name</th>
                <th>role</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.userName}</td>
                    <td><#list user.role as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.page>