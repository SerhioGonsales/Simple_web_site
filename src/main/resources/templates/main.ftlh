<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout/>
        <span><a href="/user">Редактор пользователей</a></span>
    </div>
    <div>
        <form method="post" action="/main">
            <input type="text" name="text" placeholder="type your message" />
            <input type="text" name="tag" placeholder="tag" />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">добавить</button>
        </form>
    </div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!}" />
        <button type="submit">искать</button>
    </form>
    <div><strong>Список сообщений</strong></div>
    <#list messages as message>
        <div>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
        <#else>
        Нет сообщений
    </#list>
</@c.page>