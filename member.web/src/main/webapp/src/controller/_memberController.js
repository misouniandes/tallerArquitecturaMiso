define(['model/memberModel'], function(memberModel) {
    App.Controller._MemberController = Backbone.View.extend({
        initialize: function(options) {
            this.modelClass = options.modelClass;
            this.listModelClass = options.listModelClass;
            this.showEdit = true;
            this.showDelete = true;
            this.editTemplate = _.template($('#member').html());
            this.listTemplate = _.template($('#memberList').html());
            if (!options || !options.componentId) {
                this.componentId = _.random(0, 100) + "";
            }else{
				this.componentId = options.componentId;
		    }
            var self = this;
            Backbone.on(this.componentId + '-' + 'member-create', function(params) {
                self.create(params);
            });
            Backbone.on(this.componentId + '-' + 'member-list', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'member-edit', function(params) {
                self.edit(params);
            });
            Backbone.on(this.componentId + '-' + 'member-delete', function(params) {
                self.destroy(params);
            });
            Backbone.on(this.componentId + '-' + 'post-member-delete', function(params) {
                self.list(params);
            });
            Backbone.on(this.componentId + '-' + 'member-save', function(params) {
                self.save(params);
            });
        },
        create: function() {
            if (App.Utils.eventExists(this.componentId + '-' +'instead-member-create')) {
                Backbone.trigger(this.componentId + '-' + 'instead-member-create', {view: this});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-member-create', {view: this});
                this.currentMemberModel = new this.modelClass();
                this._renderEdit();
                Backbone.trigger(this.componentId + '-' + 'post-member-create', {view: this});
            }
        },
        list: function(params) {
            if (params) {
                var data = params.data;
            }
            if (App.Utils.eventExists(this.componentId + '-' +'instead-member-list')) {
                Backbone.trigger(this.componentId + '-' + 'instead-member-list', {view: this, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-member-list', {view: this, data: data});
                var self = this;
				if(!this.memberModelList){
                 this.memberModelList = new this.listModelClass();
				}
                this.memberModelList.fetch({
                    data: data,
                    success: function() {
                        self._renderList();
                        Backbone.trigger(self.componentId + '-' + 'post-member-list', {view: self});
                    },
                    error: function(mode, error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'member-list', view: self, error: error});
                    }
                });
            }
        },
        edit: function(params) {
            var id = params.id;
            var data = params.data;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-member-edit')) {
                Backbone.trigger(this.componentId + '-' + 'instead-member-edit', {view: this, id: id, data: data});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-member-edit', {view: this, id: id, data: data});
                if (this.memberModelList) {
                    this.currentMemberModel = this.memberModelList.get(id);
                    this._renderEdit();
                    Backbone.trigger(this.componentId + '-' + 'post-member-edit', {view: this, id: id, data: data});
                } else {
                    var self = this;
                    this.currentMemberModel = new this.modelClass({id: id});
                    this.currentMemberModel.fetch({
                        data: data,
                        success: function() {
                            self._renderEdit();
                            Backbone.trigger(self.componentId + '-' + 'post-member-edit', {view: this, id: id, data: data});
                        },
                        error: function() {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'member-edit', view: self, id: id, data: data, error: error});
                        }
                    });
                }
            }
        },
        destroy: function(params) {
            var id = params.id;
            var self = this;
            if (App.Utils.eventExists(this.componentId + '-' +'instead-member-delete')) {
                Backbone.trigger(this.componentId + '-' + 'instead-member-delete', {view: this, id: id});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-member-delete', {view: this, id: id});
                var deleteModel;
                if (this.memberModelList) {
                    deleteModel = this.memberModelList.get(id);
                } else {
                    deleteModel = new this.modelClass({id: id});
                }
                deleteModel.destroy({
                    success: function() {
                        Backbone.trigger(self.componentId + '-' + 'post-member-delete', {view: self, model: deleteModel});
                    },
                    error: function() {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'member-delete', view: self, error: error});
                    }
                });
            }
        },
        save: function() {
            var self = this;
            var model = $('#' + this.componentId + '-memberForm').serializeObject();
            if (App.Utils.eventExists(this.componentId + '-' +'instead-member-save')) {
                Backbone.trigger(this.componentId + '-' + 'instead-member-save', {view: this, model : model});
            } else {
                Backbone.trigger(this.componentId + '-' + 'pre-member-save', {view: this, model : model});
                this.currentMemberModel.set(model);
                this.currentMemberModel.save({},
                        {
                            success: function(model) {
                                Backbone.trigger(self.componentId + '-' + 'post-member-save', {model: self.currentMemberModel});
                            },
                            error: function(error) {
                                Backbone.trigger(self.componentId + '-' + 'error', {event: 'member-save', view: self, error: error});
                            }
                        });
            }
        },
        _renderList: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.listTemplate({members: self.memberModelList.models, componentId: self.componentId, showEdit : self.showEdit , showDelete : self.showDelete}));
                self.$el.slideDown("fast");
            });
        },
        _renderEdit: function() {
            var self = this;
            this.$el.slideUp("fast", function() {
                self.$el.html(self.editTemplate({member: self.currentMemberModel, componentId: self.componentId , showEdit : self.showEdit , showDelete : self.showDelete
 
				}));
                self.$el.slideDown("fast");
            });
        }
    });
    return App.Controller._MemberController;
});