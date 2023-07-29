<#macro login path username isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name : </label>
            <div class="col-sm-6">
                <input class="form-control <#if userNameError??>is-invalid</#if>"
                       type="text"
                       name="${username}"
                       value="<#if user??>${user.username}</#if>"
                       placeholder="name"/>
                <#if userNameError??>
                    <div class="invalid-feedback">
                        ${userNameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password : </label>
            <div class="col-sm-6">
                <input class="form-control <#if passwordError??>is-invalid</#if>"
                       type="password"
                       name="password"
                       placeholder="password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Retype password : </label>
                <div class="col-sm-6">
                    <input class="form-control <#if password2Error??>is-invalid</#if>"
                           type="password"
                           name="password2"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email : </label>
                <div class="col-sm-6">
                    <input class="form-control <#if mailError??>is-invalid</#if>"
                           type="email"
                           name="mail"
                           value="<#if user??>${user.mail}</#if>"
                           placeholder="some@email.com"/>
                    <#if mailError??>
                        <div class="invalid-feedback">
                            ${mailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><div><a href="/registration">Добавить нового пользователя</a></div></#if>
        <div><button type="submit" class="btn btn-primary"><#if isRegisterForm>Добавить<#else>Login</#if></button></div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Выйти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>