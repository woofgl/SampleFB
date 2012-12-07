(function ($) {

    brite.registerView("MainView", {loadTmpl:true}, {
        create:function (data, config) {
            return $("#tmpl-MainView").render();
        },

        postDisplay:function (data, config) {

        },

        events:{
            "btap; .showFriends":function (event) {
                $(event.currentTarget).closest("ul").find("li").removeClass("active");
                $(event.currentTarget).closest("li").addClass("active");
                var friends = app.getFriends();
                brite.display("DataTable", ".MainView-content", {
                    gridData:friends,
                    columnDef:[
                        {
                            text:"#",
                            render: function(obj, idx){return idx + 1},
                            attrs:"style='width: 5%'"
                        },
                        {
                            text:"ID",
                            propName:"id",
                            attrs:"style='width: 15%'"

                        },
                        {
                            text:"Name",
                            render:function(obj){return obj.name},
                            attrs:"style='width: 15%'"

                        },
                        {
                            text:"Email",
                            propName:"email",
                            attrs:"style='width: 15%'"

                        },
                        {
                            text:"WebSite",
                            propName:"webSite"

                        }
                    ],
                    opts:{
                        htmlIfEmpty:"Not Friends Found",
                        withCmdDelete:false,
                        withPaging:false
                    }
                });
            },
            "btap; .showRepositories":function (event) {
                $(event.currentTarget).closest("ul").find("li").removeClass("active");
                $(event.currentTarget).closest("li").addClass("active");
                var repos = app.getRepositories();
                brite.display("DataTable", ".MainView-content", {
                    gridData:repos,
                    columnDef:[
                        {
                            text:"#",
                            render: function(obj, idx){return idx + 1},
                            attrs:"style='width: 10%'"
                        },
                        {
                            text:"Name",
                            propName:"name",
                            attrs:"style='width: 20%'"
                        },
                        {
                            text:"Url",
                            propName:"url",
                            attrs:"style='width: 30%'"
                        },
                        {
                            text:"Desc",
                            propName:"description"
                        }
                    ],
                    opts:{
                        htmlIfEmpty: "Not repository found",
                        withCmdDelete: false,
                        withPaging: false
                    }
                });
            },
            "btap; .showOrganizations":function (event) {
                $(event.currentTarget).closest("ul").find("li").removeClass("active");
                $(event.currentTarget).closest("li").addClass("active");
                var repos = app.getOrganizations();
                brite.display("DataTable", ".MainView-content", {
                    gridData:repos,
                    columnDef:[
                        {
                            text:"#",
                            render: function(obj, idx){return idx + 1},
                            attrs:"style='width: 10%'"
                        },
                        {
                            text:"Name",
                            propName:"name",
                            attrs:"style='width: 20%'"
                        },
                        {
                            text:"Location",
                            propName:"location"
                        }
                    ],
                    opts:{
                        htmlIfEmpty: "Not Organizations found",
                        withCmdDelete: false,
                        withPaging: false
                    }
                });
            }
        },

        docEvents:{
        },

        daoEvents:{
        }
    })
})(jQuery);