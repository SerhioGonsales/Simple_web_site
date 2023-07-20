<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input class="form-control" type="text" name="filter" value="${filter!}" placeholder="Поиск по тэгам" />
                <button type="submit" class="btn btn-primary ml-2">искать</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Добавьте ваше сообщение
    </a>
    <div class="collapse" id="collapseExample">
            <form method="post" action="/main" enctype="multipart/form-data">
                <div>
                    <input class="form-control mt-2 mb-2" type="text" name="text" placeholder="текст сообщения" />
                </div>
                <div>
                    <input class="form-control mb-2" type="text" name="tag" placeholder="тэг" />
                </div>
                <div>
                    <input type="file" name="file">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary mt-2">добавить</button>
                </div>
            </form>
    </div>
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
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    <strong>${message.authorName}</strong>
                </div>
            </div>
        <#else>
            Нет сообщений
        </#list>
    </div>
</@c.page>