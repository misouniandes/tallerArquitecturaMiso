define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/memberModel', 'controller/memberController'], function() {
    App.Component.MemberComponent = App.Component._CRUDComponent.extend({
        name: 'member',
        model: App.Model.MemberModel,
        listModel: App.Model.MemberList,
        controller : App.Controller.MemberController
    });
    return App.Component.MemberComponent;
});