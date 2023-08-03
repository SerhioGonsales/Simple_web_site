<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Ваше сообщение
</a>
<div class="collapse <#if message??>show</#if> " id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="/main" enctype="multipart/form-data">
            <div>
                <input class="form-control <#if textError??>is-invalid</#if> mt-2 mb-2"
                       value="<#if message??>${message.text}</#if>"
                       type="text"
                       name="text"
                       placeholder="текс сообщения"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div>
                <input class="form-control <#if tagError??>is-invalid</#if> mt-2 mb-2"
                       value="<#if message??>${message.tag}</#if>"
                       type="text"
                       name="tag"
                       placeholder="тэг для поиска"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div>
                <input type="file" name="file">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-2">Сохранить сообщение</button>
            </div>
        </form>
    </div>
</div>