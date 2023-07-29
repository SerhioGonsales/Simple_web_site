<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <div class="mb-3"><h5>Профиль пользователя <i>${name}</i></h5></div>
    <form action="/user/profile" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password : </label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="new password"/>
            </div>
        </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Email : </label>
                <div class="col-sm-6">
                    <input class="form-control" type="email" name="mail" placeholder="${user.mail!'no mail'}"/>
                </div>
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div><button type="submit" class="btn btn-primary">Сохранить новые данные</button></div>
    </form>
</@c.page>