<#macro login path username isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="${username}" placeholder="name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password : </label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="password" placeholder="password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><div><a href="/registration">Добавить нового пользователя</a></div></#if>
        <div><button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Login</#if></button></div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Выйти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>