<#include "security.ftlh">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-2">
            <#if message.filename??>
                <img class="card-img-top" src="/img/${message.filename}">
            </#if>
            <div class="m-2">
                <span>${message.text}</span>
            </div>
            <div class="m-2">
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <a href="/user/messages/${message.author.id}">${message.authorName}</a>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user/messages/${message.author.id}?message=${message.id}">
                        Редактировать
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        Нет сообщений
    </#list>
</div>