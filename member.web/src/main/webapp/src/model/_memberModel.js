define([], function() {
    App.Model._MemberModel = Backbone.Model.extend({
        defaults: {
 
		 'firstName' : ''
 ,  
		 'lastName' : ''
 ,  
		 'birthDate' : ''
 ,  
		 'docNumber' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
             if(name=='birthDate'){
                   var dateConverter = App.Utils.Converter.date;
                   return dateConverter.unserialize(this.get('birthDate'), this);
             }
         return this.get(name);
        }
    });

    App.Model._MemberList = Backbone.Collection.extend({
        model: App.Model._MemberModel,
        initialize: function() {
        }

    });
    return App.Model._MemberModel;
});