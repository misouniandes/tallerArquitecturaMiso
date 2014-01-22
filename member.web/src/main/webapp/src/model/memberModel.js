define(['model/_memberModel'], function() {
    App.Model.MemberModel = App.Model._MemberModel.extend({

    });

    App.Model.MemberList = App.Model._MemberList.extend({
        model: App.Model.MemberModel
    });

    return  App.Model.MemberModel;

});