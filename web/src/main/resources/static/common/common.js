/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */



/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 *
 * jqGrid 封装类
 * @author ThinkGem@163.COM
 * @version 2019-2-16
 */
(function ($) {
    var DataGrid = function (options, $this) {
        var dataGrid = typeof $this != "undefined" ? $this : options.dataGrid ? options.dataGrid : $("#dataGrid"),
            dataGridId = dataGrid.attr("id"),
            dataGridPage = options.dataGridPage ? options.dataGridPage : $("#" + dataGridId + "Page"),
            searchForm = options.searchForm ? options.searchForm : $("#searchForm"), options = $.extend({
                url: searchForm.attr("action"),
                postData: searchForm.serializeArray(),
                mtype: "POST",
                datatype: "json",
                jsonReader: {
                    id: options.dataId,
                    root: "list",
                    page: "pageNo",
                    userdata: "otherData",
                    total: "last",
                    records: "count",
                    subgrid: {root: "list"}
                },
                treeReader: {
                    level_field: "treeLevel",
                    parent_id_field: "parent1Code1",
                    userdata: "otherData",
                    leaf_field: "isTreeLeaf",
                    expanded_field: "isOpen",
                    icon_field: "_icon"
                },
                prmNames: {
                    page: "pageNo",
                    rows: "pageSize",
                    sort: "orderBy",
                    order: "sord",
                    search: "_search",
                    nd: "nd",
                    id: "id",
                    oper: "oper",
                    editoper: "edit",
                    addoper: "add",
                    deloper: "del",
                    subgridid: "id",
                    npage: null,
                    totalrows: "totalPage"
                },
                editurl: "clientArray",
                rowNum: -1,
                rownumWidth: 30,
                altRows: true,
                columnModel: [],
                colNames: [],
                colModel: [],
                dataId: "id",
                lazyLoad: false,
                shrinkToFit: true,
                showRownum: true,
                showCheckbox: false,
                sortableColumn: true,
                autoGridHeight: true,
                autoGridHeightFix: 0,
                autoGridWidth: true,
                autoGridWidthFix: 0,
                btnSearch: $("#btnSearch"),
                btnRefreshTree: $("#btnRefreshTree"),
                btnExpandTreeNode: $("#btnExpandTreeNode"),
                btnCollapseTreeNode: $("#btnCollapseTreeNode"),
                editGrid: false,
                editGridInitRowNum: 1,
                editGridInitAllRowEdit: true,
                editGridAddRow: function (action) {
                    if (action == "new" || action == "init") {
                        dataGrid.jqGrid("addRow", {
                            position: "last",
                            addRowParams: {keys: true, focusField: action == "new"},
                            initdata: typeof options.editGridAddRowInitData == "function" ? options.editGridAddRowInitData(dataGridId) : options.editGridAddRowInitData
                        });
                        if (typeof window.webuploaderRefresh == "function") {
                            window.webuploaderRefresh()
                        }
                    }
                },
                editGridAddRowBtn: $("#" + dataGridId + "AddRowBtn"),
                editGridAddRowInitData: {},
                editGridInputForm: dataGrid.parents("form"),
                editGridInputFormListName: "",
                editGridInputFormListAttrs: "",
                treeGrid: false,
                treeGridModel: "adjacency",
                treeColumn: null,
                ExpandColClick: true,
                ExpandColumn: options.treeColumn,
                defaultExpandLevel: 0,
                initExpandLevel: options.defaultExpandLevel,
                expandNodeClearPostData: false,
                inputPageNo: $("#pageNo", searchForm),
                inputPageSize: $("#pageSize", searchForm),
                inputOrderBy: $("#orderBy", searchForm),
                beforeRequest: function (data) {
                    switch (options.datatype.toLowerCase()) {
                        case"json":
                        case"jsonp":
                        case"xml":
                        case"script":
                            if (options.url == undefined || options.url == "") {
                                js.showMessage("设置searchFormSSSSSSS表单指定错误或URL为空。");
                                return false
                            }
                    }
                    js.loading();
                    if (options.treeGrid) {
                        // alert("treeGrid")
                        var postData = getParam("postData");
                        // alert(JSON.stringify(postData));
                        if (postData.id) {
                            // alert("1")
                            setParam({postData: {id: postData.id}})
                        } else {
                            // alert("2")
                            if (postData.nodeid) {
                                // alert("3")
                                if (options.expandNodeClearPostData == true) {
                                    alert("5")
                                    setParam({postData: {parentCode: postData.nodeid}})
                                } else {
                                    // alert("6")
                                    var ps = {parentCode: postData.nodeid};
                                    $.each(searchForm.serializeArray(), function (idx, item) {
                                        ps[item.name] = item.value
                                    });
                                    if (typeof options.expandNodeClearPostData == "string") {
                                        // alert("7")
                                        $.each(options.expandNodeClearPostData.split(","), function (idx, val) {
                                            if (val != "") {
                                                // alert("8")
                                                ps[val] = ""
                                            }
                                        })
                                    }
                                    setParam({postData: ps})
                                }
                            } else {
                                // alert("4")
                                setParam({postData: searchForm.serializeArray()})
                            }
                        }
                    } else {
                        setParam({url: searchForm.attr("action"), postData: searchForm.serializeArray()})
                    }
                    if (typeof options.ajaxLoad == "function") {
                        options.ajaxLoad(data)
                    }
                    resizeDataGrid();
                    $(".btn").attr("disabled", true);
                    $(".ui-jqgrid .loading").remove()
                },
                loadComplete: function (data) {
                    if (dataGridPage.length >= 1 && data) {
                        data.funcParam = dataGridId;
                        dataGridPage.html(pageHtml(data))
                    }
                    if (options.treeGrid) {
                        if (dataGrid.expandNodeIds) {
                            setTimeout(function () {
                                if (dataGrid.expandNodeIds.length > 0) {
                                    $("#" + dataGrid.expandNodeIds.shift() + ":visible .tree-plus", dataGrid).click()
                                } else {
                                    if (dataGrid.currentLevel < dataGrid.expandLevel) {
                                        dataGrid.currentLevel++;
                                        dataGrid.expandNodeIds = [];
                                        $(".jqgrow:visible .tree-plus", dataGrid).each(function () {
                                            var id = $(this).parents(".jqgrow").attr("id");
                                            dataGrid.expandNodeIds.push(id)
                                        });
                                        $("#" + dataGrid.expandNodeIds.shift() + ":visible .tree-plus", dataGrid).click()
                                    } else {
                                        dataGrid.expandNodeIds = null
                                    }
                                }
                            }, 10)
                        } else {
                            if (options.defaultExpandLevel && options.defaultExpandLevel > 0) {
                                expandTreeNode(options.defaultExpandLevel);
                                options.defaultExpandLevel = 0
                            }
                        }
                        setParam({postData: {id: "", nodeid: ""}})
                    }
                    if (options.editGrid) {
                        $(function () {
                            var ids = getDataIDs();
                            if (ids.length > 0) {
                                if (options.editGridInitAllRowEdit == true) {
                                    for (var i = 0; i < ids.length; i++) {
                                        dataGrid.jqGrid("editRow", ids[i], {keys: true, focusField: false})
                                    }
                                }
                            } else {
                                if (options.editGridInitRowNum && options.editGridAddRow) {
                                    for (var i = 0; i < options.editGridInitRowNum; i++) {
                                        options.editGridAddRow("init")
                                    }
                                }
                            }
                        })
                    }
                    if (typeof options.ajaxSuccess == "function") {
                        options.ajaxSuccess(data)
                    }
                    if (typeof options.btnEventBind == "function") {
                        options.btnEventBind(dataGrid.find(".btnList"));
                        options.btnEventBind($("#" + dataGridId + "_frozen").find(".btnList"))
                    }
                    if (typeof options.btnMoreEventBind == "function") {
                        options.btnMoreEventBind(dataGrid.find(".btnMore"));
                        options.btnMoreEventBind($("#" + dataGridId + "_frozen").find(".btnList"))
                    }
                    if (data && data.message) {
                        js.showMessage(data.message)
                    }
                    resizeDataGrid();
                    $(".btn").attr("disabled", false);
                    js.closeLoading()
                },
                loadError: function (data) {
                    if (typeof options.ajaxError == "function") {
                        options.ajaxError(data)
                    }
                    $(".btn").attr("disabled", false);
                    js.showErrorMessage(data.responseText);
                    js.closeLoading(0, true)
                },
                gridComplete: function () {
                    if (typeof options.complete == "function") {
                        options.complete()
                    }
                    resizeDataGrid()
                },
                onSortCol: function (index, iCol, sortorder) {
                    if (options.inputOrderBy && options.inputOrderBy.length) {
                        options.inputOrderBy.val(index + " " + sortorder)
                    }
                },
                btnEventBind: function (elements) {
                    elements.each(function () {
                        if ($(this).attr("data-click-binded") == undefined) {
                            $(this).attr("data-click-binded", true);
                            $(this).click(function () {
                                var se = $(this);
                                var url = se.attr("href");
                                var title = se.data("title");
                                if (title == undefined) {
                                    title = se.attr("title")
                                }
                                var confirm = se.data("confirm");
                                if (confirm != undefined) {
                                    js.confirm(confirm, url, function (data) {
                                        js.showMessage(data.message);
                                        if (data.result == Global.TRUE) {
                                            var confirmSuccess = se.data("confirmSuccess");
                                            if (confirmSuccess != undefined) {
                                                try {
                                                    eval(confirmSuccess)
                                                } catch (e) {
                                                    log("confirmSuccess error: " + e)
                                                }
                                            } else {
                                                if (options.treeGrid) {
                                                    var delTreeNode = se.data("deltreenode");
                                                    if (delTreeNode != undefined) {
                                                        dataGrid.dataGrid("delTreeNode", delTreeNode)
                                                    } else {
                                                        var row = getRowData(se.parents(".jqgrow").attr("id"));
                                                        if (row && !isRoot(row.parentCode)) {
                                                            alert("is root")
                                                            refreshTreeChildren(row.parentCode)
                                                        } else {
                                                            refreshTree()
                                                        }
                                                    }
                                                } else {
                                                    refresh()
                                                }
                                            }
                                        }
                                    }, "json")
                                } else {
                                    js.addTabPage($(this), title, url, options.tabPageId)
                                }
                                return false
                            })
                        }
                    });
                    return self
                },
                btnMoreEventBind: function (elements) {
                    elements.each(function () {
                        if ($(this).attr("data-mouseover-binded") == undefined) {
                            $(this).attr("data-mouseover-binded", true);
                            var timeoutHover;
                            $(this).mouseover(function () {
                                var bdiv = dataGrid.closest(".ui-jqgrid-bdiv"), more = $(this), items = more.next(),
                                    pos = more.position(), top = pos.top - items.height() / 2 + 6, left = pos.left;
                                if (!items.hasClass("moreItems")) {
                                    return
                                }
                                if (left + items.width() > bdiv.position().left + bdiv.width() - 80) {
                                    left -= items.width()
                                } else {
                                    left += more.width()
                                }
                                dataGrid.find(".btnMore.open").removeClass("open").find("i").removeClass("fa-chevron-circle-left").addClass("fa-chevron-circle-right");
                                dataGrid.find(".moreItems.open").removeClass("open").hide();
                                var itemsTop = bdiv.scrollTop() + 5;
                                if (top <= itemsTop) {
                                    top = itemsTop
                                } else {
                                    itemsTop = bdiv.height() - items.height() + itemsTop - 12;
                                    if (top >= itemsTop) {
                                        top = itemsTop
                                    }
                                }
                                more.addClass("open").find("i").removeClass("fa-chevron-circle-right").addClass("fa-chevron-circle-left");
                                items.addClass("open").css({top: top, left: left}).show();
                                if (items.attr("data-hover-binded") == undefined) {
                                    items.attr("data-hover-binded", true);
                                    items.hover(function () {
                                        window.clearTimeout(timeoutHover)
                                    }, function () {
                                        window.clearTimeout(timeoutHover);
                                        timeoutHover = window.setTimeout(function () {
                                            more.removeClass("open").find("i").removeClass("fa-chevron-circle-left").addClass("fa-chevron-circle-right");
                                            items.hide()
                                        }, 500)
                                    }).find("a").click(function () {
                                        more.removeClass("open").find("i").removeClass("fa-chevron-circle-left").addClass("fa-chevron-circle-right");
                                        items.hide()
                                    })
                                }
                            })
                        }
                    });
                    return self
                },
                ajaxLoad: function (data) {
                },
                ajaxSuccess: function (data) {
                },
                ajaxError: function (data) {
                },
                complete: function () {
                }
            }, options);
        if (options.columnModel.length > 0) {
            options.colNames = [];
            options.colModel = [];
            for (var i = 0, model; i < options.columnModel.length; i++) {
                model = options.columnModel[i];
                if (model.header) {
                    options.colNames.push(model.header)
                } else {
                    if (model.label) {
                        options.colNames.push(model.label)
                    }
                }
                options.colModel.push(model)
            }
        }
        if (options.treeGrid || !options.sortableColumn) {
            for (var i = 0; i < options.colModel.length; i++) {
                options.colModel[i].sortable = false
            }
        }
        if (options.treeGrid) {
            options.showRownum = false;
            options.showCheckbox = true
        }
        if (options.showRownum) {
            options.rownumbers = true
        }
        if (options.showCheckbox) {
            options.multiselect = true
        }
        if (options.datatype == "local" && options.rowNum == -1) {
            options.rowNum = 5000
        }
        if (options.lazyLoad && !options.treeGrid) {
            options.datatype_bak = options.datatype;
            options.datatype = "local"
        }
        if (options.groupHeaders || options.frozenCols || !options.shrinkToFit) {
            options.shrinkToFit = false
        } else {
            options.shrinkToFit = true
        }
        if (options.showFooter) {
            options.footerrow = true;
            options.userDataOnFooter = true
        }
        if (options.inputPageNo.length == 0) {
            var pageNo = searchForm.data("pageNo");
            searchForm.append('<input id="pageNo" name="pageNo" type="hidden" value="' + (pageNo ? pageNo : "") + '"/>');
            options.inputPageNo = $("#pageNo", searchForm)
        }
        if (options.inputPageSize.length == 0) {
            var pageSize = searchForm.data("pageSize");
            searchForm.append('<input id="pageSize" name="pageSize" type="hidden" value="' + (pageSize ? pageSize : "") + '"/>');
            options.inputPageSize = $("#pageSize", searchForm)
        }
        if (options.inputOrderBy.length == 0) {
            var orderBy = searchForm.data("orderBy");
            searchForm.append('<input id="orderBy" name="orderBy" type="hidden" value="' + (orderBy ? orderBy : "") + '"/>');
            options.inputOrderBy = $("#orderBy", searchForm)
        }
        if (searchForm && searchForm.length > 0) {
            searchForm.submit(function () {
                refresh();
                return false
            }).on("reset", function () {
                setTimeout(function () {
                    if ($.fn.iCheck !== undefined) {
                        searchForm.find("input[type=checkbox].form-control:not(.noicheck),input[type=radio].form-control:not(.noicheck)").iCheck("update")
                    }
                    if ($.fn.select2 !== undefined) {
                        searchForm.find("select.form-control:not(.noselect2)").trigger("change")
                    }
                    searchForm.find(".isReset").each(function () {
                        $(this).val($(this).data("defaultValue"))
                    })
                }, 200)
            })
        }
        if (typeof options.btnEventBind == "function") {
            options.btnEventBind($(".btnTool"))
        }
        if (options.btnSearch.length > 0) {
            options.btnSearch.unbind("click").click(function () {
                var btnSearch = $(this);
                if (searchForm.hasClass("hide")) {
                    searchForm.removeClass("hide");
                    btnSearch.addClass("active");
                    btnSearch.html(btnSearch.html().replace("查询", "隐藏").replace($.jgrid.extend.btnSearch, $.jgrid.extend.btnHideSearch))
                } else {
                    searchForm.addClass("hide");
                    btnSearch.removeClass("active");
                    btnSearch.html(btnSearch.html().replace("隐藏", "查询").replace($.jgrid.extend.btnHideSearch, $.jgrid.extend.btnSearch))
                }
                resizeDataGrid();
                return false
            });
            if (!searchForm.hasClass("hide")) {
                var btnSearch = options.btnSearch;
                searchForm.removeClass("hide");
                btnSearch.addClass("active");
                btnSearch.html(btnSearch.html().replace("查询", "隐藏").replace($.jgrid.extend.btnSearch, $.jgrid.extend.btnHideSearch))
            }
        }
        if (options.treeGrid) {
            options.btnRefreshTree.unbind("click").click(function () {
                searchForm.each(function () {
                    this.reset()
                });
                refreshTree();
                return false
            });
            options.btnExpandTreeNode.unbind("click").click(function () {
                expandTreeNode(1);
                return false
            });
            options.btnCollapseTreeNode.unbind("click").click(function () {
                collapseTreeNode();
                return false
            })
        }
        if (options.editGrid) {
            options.editGridAddRowBtn.unbind("click").click(function () {
                if (options.editGridAddRow) {
                    options.editGridAddRow("new")
                }
                return false
            });
            if (options.editGridInputForm && options.editGridInputForm.length > 0 && options.editGridInputFormListName != "" && options.editGridInputFormListAttrs != "") {
                options.editGridInputForm.submit(function () {
                    updateListFieldName()
                })
            }
        }

        dataGrid.jqGrid(options);
        if (options.groupHeaders && options.groupHeaders.twoLevel) {
            if (options.groupHeaders.threeLevel) {
                dataGrid.jqGrid("setComplexHeaders", {
                    complexHeaders: {
                        defaultStyle: true,
                        twoLevel: options.groupHeaders.twoLevel,
                        threeLevel: options.groupHeaders.threeLevel
                    }
                })
            } else {
                dataGrid.jqGrid("setGroupHeaders", {useColSpanStyle: true, groupHeaders: options.groupHeaders.twoLevel})
            }
        }
        if (options.frozenCols) {
            dataGrid.jqGrid("setFrozenColumns")
        }
        if (!window.dataGrids) {
            window.dataGrids = []
        }
        window.dataGrids.push(dataGrid);
        $(window).resizeEnd(function () {
            for (var i in window.dataGrids) {
                window.dataGrids[i].dataGrid("resize")
            }
        });
        resizeDataGrid();

        function setGridHeight() {
            if (!options.autoGridHeight) {
                return
            }
            var gridHeight = 0;
            if (typeof options.autoGridHeight == "function") {
                gridHeight = options.autoGridHeight()
            } else {
                var tabContent = $(dataGrid).parents(".tab-content");
                if (tabContent.length > 0) {
                    var portlet = $(dataGrid).parents(".box.tabbable.autoHeight:not(.default)");
                    if (portlet.length > 0) {
                        var headerHeight = $(dataGrid).parents(".ui-jqgrid").find(".ui-jqgrid-hdiv").height();
                        if (headerHeight == 0) {
                            headerHeight = 43;
                            if (options.groupHeaders && options.groupHeaders.twoLevel) {
                                if (options.groupHeaders.threeLevel) {
                                    headerHeight *= 3
                                } else {
                                    headerHeight *= 2
                                }
                            }
                        }
                        gridHeight = tabContent.height() - headerHeight - options.autoGridHeightFix
                    } else {
                        gridHeight = 300
                    }
                } else {
                    var gridHeight = $(dataGrid).height();
                    var gridParent = $(dataGrid).parent();
                    if (gridParent.length != 0) {
                        gridHeight = gridParent.height()
                    }
                    gridHeight = ($(window).height() - $("body").height() + gridHeight - options.autoGridHeightFix);
                    if (gridHeight < 200) {
                        gridHeight = 200
                    }
                    gridParent.height(gridHeight)
                }
            }
            if (gridHeight != 0) {
                dataGrid.jqGrid("setGridHeight", gridHeight)
            }
        }

        function setGridWidth() {
            if (!options.autoGridWidth) {
                return
            }
            var gridWidth = 0;
            if (typeof options.autoGridWidth == "function") {
                gridWidth = options.autoGridWidth()
            } else {
                var jqGridParent = $(dataGrid).parents(".ui-jqgrid").parent();
                if (jqGridParent.is(":visible")) {
                    gridWidth = jqGridParent.width() - 2
                }
            }
            if (gridWidth != 0) {
                dataGrid.jqGrid("setGridWidth", gridWidth - options.autoGridWidthFix, (options.shrinkToFit && $(window).width() > 500))
            }
        }

        function resizeDataGrid() {
            setGridHeight();
            setGridWidth();
            setTimeout(function () {
                setGridHeight();
                setGridWidth()
            }, (!!navigator.userAgent.match(/MSIE 8.0/)) ? 200 : 100)
        }

        function isRoot(code) {
            return !code || code == "" || code == "0"
        }

        function getParam(paramName) {
            return dataGrid.jqGrid("getGridParam", paramName)
        }

        function setParam(params, overwrite) {
            return dataGrid.jqGrid("setGridParam", params, overwrite)
        }

        function getDataIDs() {
            return dataGrid.jqGrid("getDataIDs")
        }

        function getRowData(id) {
            return dataGrid.jqGrid("getRowData", id)
        }

        function getSelectRow() {
            return getParam("selrow")
        }

        function getSelectRows() {
            return getParam("selarrrow")
        }

        function setSelectRow(id, isCancel) {
            if (id == undefined && isCancel == undefined) {
                dataGrid.jqGrid("resetSelection")
            } else {
                if (id != undefined && isCancel) {
                    dataGrid.jqGrid("resetSelection", id)
                } else {
                    dataGrid.jqGrid("setSelection", id)
                }
            }
            return self
        }

        function refresh(pageNo, pageSize) {
            if (pageNo) {
                options.inputPageNo.val(pageNo)
            }
            if (pageSize) {
                options.inputPageSize.val(pageSize)
            }
            var params = {};
            if (options.inputPageNo.val() != "") {
                params.page = options.inputPageNo.val()
            }
            if (options.inputPageSize.val() != "") {
                params.rowNum = options.inputPageSize.val()
            }
            if (options.lazyLoad) {
                options.datatype = params.datatype = options.datatype_bak
            }
            setParam(params);
            reloadGrid();
            return self
        }

        function reloadGrid() {
            dataGrid.trigger("reloadGrid")
        }

        function delRowData(id) {
            dataGrid.jqGrid("delRowData", id);
            return self
        }

        function delTreeNode(id) {
            dataGrid.jqGrid("delTreeNode", id);
            return self
        }

        function expandTreeNode(level) {
            js.loading();
            setTimeout(function () {
                dataGrid.expandLevel = level;
                dataGrid.currentLevel = 1;
                dataGrid.expandNodeIds = [];
                $(".jqgrow:visible .tree-plus", dataGrid).each(function () {
                    var id = $(this).parents(".jqgrow")[0].id;
                    dataGrid.expandNodeIds.push(id)
                });
                if (dataGrid.expandNodeIds.length > 100) {
                    js.showMessage($.jgrid.format($.jgrid.extend.expandTooMany, dataGrid.expandNodeIds.length));
                    dataGrid.expandNodeIds = []
                } else {
                    $("#" + dataGrid.expandNodeIds.shift() + ":visible .tree-plus", dataGrid).click()
                }
                for (var i = 0; i < dataGrid.expandLevel; i++) {
                    $(".jqgrow:visible .tree-plus", dataGrid).click()
                }
                js.closeLoading()
            }, 10);
            return self
        }

        function collapseTreeNode() {
            js.loading();
            setTimeout(function () {
                $(".tree-minus", dataGrid).click();
                js.closeLoading()
            }, 10);
            return self
        }

        function refreshTree(expandLevel, parentCode) {
            alert("refreshTree")
            console.log("expandLevel : " + expandLevel)
            console.log("parentCode : " + parentCode)
            if (expandLevel) {
                options.defaultExpandLevel = expandLevel
            }
            if (!isRoot(parentCode)) {
                setParam({postData: {id: parentCode}})
            } else {
                if (parentCode != undefined) {
                    options.defaultExpandLevel = options.initExpandLevel
                }
            }
            reloadGrid();
            return self
        }

        function refreshTreeChildren(rowid, currentRowid) {
            // alert("refreshTreeChildren")
            if (currentRowid) {
                delTreeNode(currentRowid)
            }
            if (isRoot(rowid)) {
                reloadGrid()
            } else {
                if (rowid != "") {
                    var node = dataGrid.find("#" + rowid + " .treeclick");
                    if (node.length > 0) {
                        node.dblclick()
                    } else {
                        reloadGrid()
                    }
                }
            }
        }

        function mergeCell(cellNames) {
            var trs = $("#" + dataGridId + ">tbody>tr:gt(0)");
            $.each(cellNames.split(","), function (idx, name) {
                var bg = trs.eq(0).children("[aria-describedby='" + dataGridId + "_" + name + "']"), index = bg.index(),
                    rowsp = 1;
                trs.slice(1).each(function (ind2, tr) {
                    var me = $(tr).children("td").eq(index);
                    if (bg.text() === me.text()) {
                        rowsp++;
                        me.hide()
                    } else {
                        bg.attr("rowspan", rowsp);
                        bg = me;
                        rowsp = 1
                    }
                    bg.attr("rowspan", rowsp)
                })
            })
        }

        function updateListFieldName(listName, fieldNames) {
            listName = listName || options.editGridInputFormListName;
            fieldNames = fieldNames || options.editGridInputFormListAttrs;
            $.each(fieldNames.split(","), function (idx, fieldName) {
                if (fieldName != "") {
                    var inputs = ['[name="' + fieldName + '"]:not(div,a,script)', '[name^="' + listName + '["][name$="].' + fieldName + '"]:not(div,a,script)'];
                    dataGrid.find(inputs.join(",")).each(function (idx, val) {
                        $(this).attr("name", listName + "[" + idx + "]." + fieldName)
                    });
                    var defaultInputs = ['[name="!' + fieldName + '"]:not(div,a,script)', '[name^="!' + listName + '["][name$="].' + fieldName + '"]:not(div,a,script)'];
                    dataGrid.find(defaultInputs.join(",")).each(function (idx, val) {
                        $(this).attr("name", "!" + listName + "[" + idx + "]." + fieldName)
                    })
                }
            })
        }

        var self = {
            jqGrid: function (option, value, v2, v3, v4, v5) {
                alert("jqGrid : ")
                return dataGrid.jqGrid(option, value, v2, v3, v4, v5)
            }, setParam: function (params, overwrite) {
                return setParam(params, overwrite)
            }, getParam: function (paramName) {
                return getParam(paramName)
            }, getDataIDs: function () {
                return getDataIDs()
            }, getRowData: function (rowId) {
                return getRowData(rowId)
            }, getSelectRow: function () {
                return getSelectRow()
            }, getSelectRows: function () {
                return getSelectRows()
            }, setSelectRow: function (id, isCancel) {
                return setSelectRow(id, isCancel)
            }, resize: function () {
                return resizeDataGrid()
            }, refresh: function (pageNo, pageSize) {
                return refresh(pageNo, pageSize)
            }, reloadGrid: function () {
                return reloadGrid()
            }, delRowData: function (id) {
                return delRowData(id)
            }, delTreeNode: function (id) {
                return delTreeNode(id)
            }, expandTreeNode: function (level) {
                return expandTreeNode(level)
            }, collapseTreeNode: function () {
                collapseTreeNode()
            }, refreshTree: function (expandLevel, parentCode) {
                alert("refreshTree")
                return refreshTree(expandLevel, parentCode)
            }, refreshTreeChildren: function (rowid, currentRowid) {
                return refreshTreeChildren(rowid, currentRowid)
            }, mergeCell: function (cellNames) {
                return mergeCell(cellNames)
            }, updateListFieldName: function (listName, fieldNames) {
                updateListFieldName(listName, fieldNames)
            }
        };
        return self
    };
    $.fn.dataGrid = function (option, value, v2, v3, v4, v5) {
        var method_call;
        var $set = this.each(function () {
            var $this = $(this);
            var data = $this.data("dataGrid");
            var options = typeof option === "object" && option;
            if (!data) {
                data = new DataGrid(options, $this);
                $this.data("dataGrid", data)
            }
            if (typeof option === "string" && typeof data[option] === "function") {
                if (value instanceof Array) {
                    method_call = data[option].apply(data, value, v2, v3, v4, v5)
                } else {
                    method_call = data[option](value, v2, v3, v4, v5)
                }
            }
        });
        if (method_call === undefined) {
            var fn = $.jgrid.getMethod(option);
            if (fn) {
                var args = $.makeArray(arguments).slice(1);
                method_call = fn.apply(this, args)
            }
        }
        return (method_call === undefined) ? $set : method_call
    };
    $.extend($.jgrid, {
        stripHtml: function (v) {
            v = String(v);
            return v.replace(/<[^>]*>/g, "")
        }
    })
})(jQuery);

function page(b, a, c) {
    try {
        if (c && c != "") {
            $("#" + c).dataGrid("refresh", b, a)
        } else {
            $(".ui-jqgrid-btable:eq(0)").dataGrid("refresh", b, a)
        }
    } catch (d) {
    }
    return false
}

function pageHtml(c) {
    if (!c.pageNo) {
        c.pageNo = 1
    }
    if (!c.pageSize) {
        c.pageSize = 20
    }
    if (!c.count) {
        c.count = 0
    }
    if (!c.bothNum) {
        c.bothNum = 1
    }
    if (!c.centerNum) {
        c.centerNum = 5
    }
    if (!c.funcName) {
        c.funcName = "page"
    }
    if (!c.funcParam) {
        c.funcParam = ""
    }
    if (!c.pageInfo) {
        c.pageInfo = ""
    }
    if (c.pageSize <= 0) {
        c.pageSize = 20
    }
    c.first = 1;
    c.last = parseInt(c.count / c.pageSize) + c.first - 1;
    if (c.count % c.pageSize != 0 || c.last == 0) {
        c.last++
    }
    if (c.last < c.first) {
        c.last = c.first
    }
    if (c.pageNo <= c.first) {
        c.pageNo = c.first
    }
    if (c.pageNo >= c.last) {
        c.pageNo = c.last
    }
    if (c.pageNo > 1) {
        c.prev = c.pageNo - 1
    } else {
        c.prev = c.first
    }
    if (c.pageNo < c.last - 1) {
        c.next = c.pageNo + 1
    } else {
        c.next = c.last
    }
    c.centerBegin = c.pageNo - parseInt(c.centerNum / 2);
    if (c.centerBegin < c.first) {
        c.centerBegin = c.first
    }
    c.centerEnd = c.centerBegin + c.centerNum - 1;
    if (c.centerEnd >= c.last) {
        c.centerEnd = c.last;
        c.centerBegin = c.centerEnd - c.centerNum + 1
    }
    if (c.centerBegin == c.first) {
        c.centerEnd = c.centerEnd + c.bothNum
    }
    if (c.centerEnd == c.last) {
        c.centerBegin = c.centerBegin - c.bothNum
    }
    if (!(c.last - c.centerEnd > c.bothNum)) {
        c.centerBegin--
    }
    if (c.centerBegin < c.first) {
        c.centerBegin = c.first
    }
    var b = ['<ul class="pagination">\n'], a = 0;
    if (c.pageNo == c.first) {
        b.push('<li class="disabled"><a href="javascript:"><i class="fa fa-angle-left"></i></a></li>\n')
    } else {
        b.push('<li><a href="javascript:" onclick="' + c.funcName + "(" + c.prev + "," + c.pageSize + ",'" + c.funcParam + '\');"> <i class="fa fa-angle-left"></i></a></li>\n')
    }
    for (a = c.first; a < c.first + c.bothNum && a < c.centerBegin; a++) {
        b.push('<li><a href="javascript:" onclick="' + c.funcName + "(" + a + "," + c.pageSize + ",'" + c.funcParam + "');\">" + (a + 1 - c.first) + "</a></li>\n")
    }
    if (a < c.centerBegin) {
        b.push('<li class="disabled"><a href="javascript:">...</a></li>\n')
    } else {
        c.centerEnd++
    }
    if (c.centerEnd > c.last) {
        c.centerEnd = c.last
    }
    for (a = c.centerBegin; a <= c.centerEnd; a++) {
        if (a == c.pageNo) {
            b.push('<li class="active"><a href="javascript:">' + (a + 1 - c.first) + "</a></li>\n")
        } else {
            b.push('<li><a href="javascript:" onclick="' + c.funcName + "(" + a + "," + c.pageSize + ",'" + c.funcParam + "');\">" + (a + 1 - c.first) + "</a></li>\n")
        }
    }
    if (c.last - c.centerEnd > c.bothNum) {
        b.push('<li class="disabled"><a href="javascript:">...</a></li>\n');
        c.centerEnd = c.last - c.bothNum
    }
    for (a = c.centerEnd + 1; a <= c.last; a++) {
        b.push('<li><a href="javascript:" onclick="' + c.funcName + "(" + a + "," + c.pageSize + ",'" + c.funcParam + "');\">" + (a + 1 - c.first) + "</a></li>\n")
    }
    if (c.pageNo == c.last) {
        b.push('<li class="disabled"><a href="javascript:"><i class="fa fa-angle-right"></i></a></li>\n')
    } else {
        b.push('<li><a href="javascript:" onclick="' + c.funcName + "(" + c.next + "," + c.pageSize + ",'" + c.funcParam + '\');"><i class="fa fa-angle-right"></i></a></li>\n')
    }
    b.push("</ul>\n");
    b.push(pageHtmlControl(c));
    b.push('<div style="clear:both;"></div>');
    return b.join("")
}

function pageHtmlControl(b) {
    var a = ['<ul class="pagination">\n'];
    a.push('<li class="disabled controls" title="' + $.jgrid.extend.pageTitle + '">' + $.jgrid.extend.pageLabelA + " ");
    a.push('<input type="text" value="' + b.pageNo + '" onkeypress="var e=window.event||event;var c=e.keyCode||e.which;if(c==13)');
    a.push(b.funcName + "(this.value," + b.pageSize + ",'" + b.funcParam + '\');" onclick="this.select();"/> ' + $.jgrid.extend.pageLabelB + " ");
    a.push('<input type="text" value="' + b.pageSize + '" onkeypress="var e=window.event||event;var c=e.keyCode||e.which;if(c==13)');
    a.push(b.funcName + "(" + b.pageNo + ",this.value,'" + b.funcParam + '\');" onclick="this.select();"/> ');
    a.push($.jgrid.format($.jgrid.extend.pageLabelC, b.count));
    a.push((b.pageInfo && b.pageInfo != "" ? b.pageInfo : "") + "</li>\n");
    a.push("</ul>\n");
    return a.join("")
}

(function (a) {
    a.jgrid.extend({
        getIdsByLevel: function (b) {
            var c = [];
            this.each(function () {
                var g = this;
                if (!g.grid || !g.p.treeGrid) {
                    return
                }
                var l = g.p.expColInd, k = g.p.treeReader.expanded_field, e = g.p.treeReader.leaf_field,
                    d = g.p.treeReader.level_field, m = g.p.treeReader.icon_field, j = g.p.treeReader.loaded;
                var h = g.p.data;
                for (var f = 0; f < h.length; f++) {
                    if (!h[f][e] && h[f][d] == b) {
                        c.push(h[f]._id_)
                    }
                }
            });
            return c
        }, delTreeChildNode: function (b) {
            return this.each(function () {
                var h = this, q = h.p.localReader.id, j, g = h.p.treeReader.left_field, p = h.p.treeReader.right_field,
                    l = h.p.treeReader.expanded_field, k = h.p.treeReader.loaded, d, e, m, o;
                if (!h.grid || !h.p.treeGrid) {
                    return
                }
                var n = h.p._index[b];
                if (n !== undefined) {
                    var c = h.p.data[n];
                    var f = a(h).jqGrid("getNodeChildren", c);
                    if (f.length > 0) {
                        for (j = 0; j < f.length; j++) {
                            a(h).jqGrid("delRowData", f[j][q])
                        }
                    }
                    a(h).jqGrid("collapseRow", c);
                    a(h).jqGrid("collapseNode", c);
                    c[k] = undefined
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        setComplexHeaders: function (b) {
            b = a.extend({complexHeaders: {defaultStyle: true, threeLevel: [], twoLevel: []}}, b || {});
            return this.each(function () {
                var l = b.complexHeaders, j = l.threeLevel, g = l.twoLevel;
                if (j.length === 0 || g.length === 0) {
                    return
                }
                this.p.complexHeader = b;
                var K = this, p = l.defaultStyle === undefined ? true : l.defaultStyle, G, E, n, J, k, d, F, x, M, u, c,
                    C, r = 0, H = 0, A = false, B, v, w, f, y = K.p.colModel, D = y.length, t = K.grid.headers,
                    h = a("table.ui-jqgrid-htable", K.grid.hDiv),
                    m = h.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header"),
                    e = h.children("thead"), s, q, o = h.find(".jqg-first-row-header");
                if (o[0] === undefined) {
                    o = a("<tr>", {
                        role: "row",
                        "aria-hidden": "true"
                    }).addClass("jqg-first-row-header").css("height", "auto")
                } else {
                    o.empty()
                }
                var z, I = function (Q, O) {
                    var N = 0, P = O.length;
                    for (; N < P; N++) {
                        if (O[N] && O[N].startColumnName === Q) {
                            return N
                        }
                    }
                    return -1
                };
                a(K).prepend(e);
                n = a("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");
                J = a("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-four-row-header");
                for (G = 0; G < D; G++) {
                    d = t[G].el;
                    F = a(d);
                    E = y[G];
                    x = {height: "0px", width: t[G].width + "px", display: (E.hidden ? "none" : "")};
                    a("<th>", {role: "gridcell"}).css(x).addClass("ui-first-th-" + K.p.direction).appendTo(o);
                    d.style.width = "";
                    c = I(E.name, j);
                    if (c >= 0) {
                        B = j[c];
                        v = parseInt(B.numberOfColumns, 10);
                        w = B.titleText;
                        for (f = 0, M = 0; M < v && (G + M < D); M++) {
                            if (!y[G + M].hidden) {
                                f++
                            }
                        }
                        k = a("<th>").attr({
                            role: "columnheader",
                            noWrap: true
                        }).addClass("ui-state-default ui-th-column-header ui-th-" + K.p.direction + " " + (B.classes || "")).css({
                            height: "22px",
                            "border-top": "0px none",
                            overflow: "hidden"
                        }).html(w);
                        if (f > 0) {
                            k.attr("colspan", String(f))
                        }
                        if (K.p.headertitles) {
                            k.attr("title", k.text())
                        }
                        if (f === 0) {
                            k.hide()
                        }
                        F.before(k);
                        r = v;
                        if (p === false) {
                            A = false;
                            for (var L = 0; L < r && (L + G < D); L++) {
                                u = I(y[L + G].name, g);
                                if (u >= 0) {
                                    B = g[u];
                                    v = parseInt(B.numberOfColumns, 10);
                                    for (M = 0; M < v && (L + G + M < D); M++) {
                                        if (!y[L + G + M].hidden) {
                                            A = true;
                                            break
                                        }
                                    }
                                    if (A === true) {
                                        break
                                    }
                                }
                            }
                        }
                    }
                    C = I(E.name, g);
                    if (r > 0 && C >= 0) {
                        B = g[C];
                        v = parseInt(B.numberOfColumns, 10);
                        w = B.titleText;
                        for (f = 0, M = 0; M < v && (G + M < D); M++) {
                            if (!y[G + M].hidden) {
                                f++
                            }
                        }
                        k = a("<th>").attr({
                            role: "columnheader",
                            noWrap: true
                        }).addClass("ui-state-default ui-th-column-header ui-th-" + K.p.direction).css({
                            height: "22px",
                            "border-top": "0px none",
                            overflow: "hidden"
                        }).html(w);
                        if (f > 0) {
                            k.attr("colspan", String(f))
                        }
                        if (K.p.headertitles) {
                            k.attr("title", k.text())
                        }
                        if (f === 0) {
                            k.hide()
                        }
                        n.append(k);
                        H = v
                    }
                    if (r === 0) {
                        F.attr("rowspan", "3");
                        H = 0;
                        continue
                    }
                    if (r > 0 && H === 0) {
                        if (p) {
                            F.attr("rowspan", "2");
                            n.append(d)
                        } else {
                            if (A) {
                                F.attr("rowspan", "2");
                                n.append(d)
                            } else {
                                k.attr("rowspan", "2");
                                J.append(d)
                            }
                        }
                        r--;
                        continue
                    }
                    if (r > 0 && H > 0) {
                        J.append(d);
                        r--;
                        H--
                    }
                }
                s = a(K).children("thead");
                s.prepend(o);
                n.insertAfter(m);
                J.insertAfter(n);
                h.append(s);
                h.find("span.ui-jqgrid-resize").each(function () {
                    var i = a(this).parent();
                    if (i.is(":visible")) {
                        this.style.cssText = "height: " + (i.height()) + "px !important; cursor: col-resize;"
                    }
                });
                h.find("div.ui-jqgrid-sortable").each(function () {
                    var i = a(this), N = i.parent();
                    if (N.is(":visible") && N.is(":has(span.ui-jqgrid-resize)")) {
                        i.css("top", (N.height() - i.outerHeight()) / 2 + "px")
                    }
                });
                z = s.find("tr.jqg-first-row-header");
                a(K).bind("jqGridResizeStop.setGroupHeaders", function (O, N, i) {
                    z.find("th").eq(i).width(N)
                })
            })
        }, createFrozenHtable: function (e, g) {
            var P = this[0], I = P.p.complexHeader, m = I.complexHeaders, q = m.defaultStyle, k = m.threeLevel,
                j = m.twoLevel, H = P.p.id, A = P.p.colModel, F = e + 1, t = P.grid.headers, r = 0, L = 0, C = false, K,
                G, z, d, J, y, B, R, l, D, u, b, E, v, w, h, c = P.grid.fhDiv.height(),
                M = a("<table class='ui-jqgrid-htable' style='width:1px;height:" + c + "px' role='grid' aria-labelledby='gbox_" + H + "' cellspacing='0' cellpadding='0' border='0'></table>"),
                f = a("<thead></thead>"), p = a("<tr>", {
                    role: "row",
                    "aria-hidden": "true"
                }).addClass("jqg-first-row-header").css("height", "auto"),
                n = a("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-second-row-header"),
                o = a("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header"),
                O = a("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels jqg-four-row-header"),
                N = function (V, T) {
                    var S = 0, U = T.length;
                    for (; S < U; S++) {
                        if (T[S] && T[S].startColumnName === V) {
                            return S
                        }
                    }
                    return -1
                };
            for (K = 0; K < F; K++) {
                G = A[K];
                y = G.name;
                J = a("#" + H + "_" + y, g);
                z = {height: "0px", width: t[K].width + "px", display: (G.hidden ? "none" : "")};
                a("<th>", {role: "gridcell"}).css(z).addClass("ui-first-th-" + P.p.direction).appendTo(p);
                b = N(G.name, k);
                if (b >= 0) {
                    D = k[b];
                    v = parseInt(D.numberOfColumns, 10);
                    if (v + K - 1 > e) {
                        v = e - K + 1
                    }
                    w = D.titleText;
                    for (h = 0, R = 0; R < v && (K + R < F); R++) {
                        if (!A[K + R].hidden) {
                            h++
                        }
                    }
                    l = a("<th>").attr({
                        role: "columnheader",
                        noWrap: true
                    }).addClass("ui-state-default ui-th-column-header ui-th-" + P.p.direction).css({
                        height: "22px",
                        "border-top": "0px none",
                        overflow: "hidden"
                    }).html(w);
                    if (h > 0) {
                        l.attr("colspan", String(h))
                    }
                    if (P.p.headertitles) {
                        l.attr("title", l.text())
                    }
                    if (h === 0) {
                        l.hide()
                    }
                    l.appendTo(n);
                    r = v;
                    if (q === false) {
                        C = false;
                        for (var Q = 0; Q < r && (Q + K < F); Q++) {
                            u = N(A[Q + K].name, j);
                            if (u >= 0) {
                                D = j[u];
                                v = parseInt(D.numberOfColumns, 10);
                                if (v + Q + K - 1 > e) {
                                    v = e - (Q + K) + 1
                                }
                                for (R = 0; R < v && (Q + K + R < F); R++) {
                                    if (!A[Q + K + R].hidden) {
                                        C = true;
                                        break
                                    }
                                }
                            }
                            if (C === true) {
                                break
                            }
                        }
                    }
                }
                E = N(G.name, j);
                if (r > 0 && E >= 0) {
                    D = j[E];
                    v = parseInt(D.numberOfColumns, 10);
                    if (v + K - 1 > e) {
                        v = e - K + 1
                    }
                    w = D.titleText;
                    for (h = 0, R = 0; R < v && (K + R < F); R++) {
                        if (!A[K + R].hidden) {
                            h++
                        }
                    }
                    l = a("<th>").attr({
                        role: "columnheader",
                        noWrap: true
                    }).addClass("ui-state-default ui-th-column-header ui-th-" + P.p.direction).css({
                        height: "22px",
                        "border-top": "0px none",
                        overflow: "hidden"
                    }).html(w);
                    if (h > 0) {
                        l.attr("colspan", String(h))
                    }
                    if (P.p.headertitles) {
                        l.attr("title", l.text())
                    }
                    if (h === 0) {
                        l.hide()
                    }
                    l.appendTo(o);
                    L = v
                }
                if (r === 0) {
                    J.attr("rowspan", "3");
                    J.appendTo(n);
                    L = 0;
                    continue
                }
                if (r > 0 && L === 0) {
                    if (q) {
                        J.attr("rowspan", "2");
                        J.appendTo(o)
                    } else {
                        if (C) {
                            J.attr("rowspan", "2");
                            J.appendTo(o)
                        } else {
                            l.attr("rowspan", "2");
                            J.appendTo(O)
                        }
                    }
                    r--;
                    continue
                }
                if (r > 0 && L > 0) {
                    J.appendTo(O);
                    r--;
                    L--
                }
            }
            var x = o.children().length, s = O.children().length;
            if (x === 0 && s === 0) {
                n.height(c)
            }
            if (q) {
                if (x > 0 && s === 0) {
                    o.height(c - 23)
                }
            } else {
                if (x === 0 && s > 0) {
                    n.height(c - 23);
                    n.find("th").each(function () {
                        var i = a(this).attr("rowspan");
                        if (i) {
                            a(this).attr("rowspan", String(parseInt(i, 10) - 1))
                        }
                    })
                }
            }
            p.appendTo(f);
            n.appendTo(f);
            x && o.appendTo(f);
            s && O.appendTo(f);
            f.appendTo(M);
            return M
        }
    })
})(jQuery);
(function (a) {
    a.jgrid = a.jgrid || {};
    a.extend(a.jgrid, {
        defaults: {
            recordtext: "{0} - {1}\u3000共 {2} 条",
            emptyrecords: "无数据显示",
            loadtext: "正在加载...",
            pgtext: " {0} 共 {1} 页",
            pgfirst: "First Page",
            pglast: "Last Page",
            pgnext: "Next Page",
            pgprev: "Previous Page",
            pgrecs: "Records per Page",
            showhide: "Toggle Expand Collapse Grid"
        },
        search: {
            caption: "搜索...",
            Find: "查找",
            Reset: "重置",
            odata: [{oper: "eq", text: "等于\u3000\u3000"}, {oper: "ne", text: "不等\u3000\u3000"}, {
                oper: "lt",
                text: "小于\u3000\u3000"
            }, {oper: "le", text: "小于等于"}, {oper: "gt", text: "大于\u3000\u3000"}, {
                oper: "ge",
                text: "大于等于"
            }, {oper: "bw", text: "开始于"}, {oper: "bn", text: "不开始于"}, {oper: "in", text: "属于\u3000\u3000"}, {
                oper: "ni",
                text: "不属于"
            }, {oper: "ew", text: "结束于"}, {oper: "en", text: "不结束于"}, {oper: "cn", text: "包含\u3000\u3000"}, {
                oper: "nc",
                text: "不包含"
            }, {oper: "nu", text: "不存在"}, {oper: "nn", text: "存在"}],
            groupOps: [{op: "AND", text: "所有"}, {op: "OR", text: "任一"}],
            operandTitle: "Click to select search operation.",
            resetTitle: "Reset Search Value"
        },
        edit: {
            addCaption: "添加记录",
            editCaption: "编辑记录",
            bSubmit: "提交",
            bCancel: "取消",
            bClose: "关闭",
            saveData: "数据已改变，是否保存？",
            bYes: "是",
            bNo: "否",
            bExit: "取消",
            msg: {
                required: "此字段必需",
                number: "请输入有效数字",
                minValue: "输值必须大于等于 ",
                maxValue: "输值必须小于等于 ",
                email: "这不是有效的e-mail地址",
                integer: "请输入有效整数",
                date: "请输入有效时间",
                url: "无效网址。前缀必须为 ('http://' 或 'https://')",
                nodefined: " 未定义！",
                novalue: " 需要返回值！",
                customarray: "自定义函数需要返回数组！",
                customfcheck: "必须有自定义函数!"
            }
        },
        view: {caption: "查看记录", bClose: "关闭"},
        del: {caption: "删除", msg: "删除所选记录？", bSubmit: "删除", bCancel: "取消"},
        nav: {
            edittext: "",
            edittitle: "编辑所选记录",
            addtext: "",
            addtitle: "添加新记录",
            deltext: "",
            deltitle: "删除所选记录",
            searchtext: "",
            searchtitle: "查找",
            refreshtext: "",
            refreshtitle: "刷新表格",
            alertcap: "注意",
            alerttext: "请选择记录",
            viewtext: "",
            viewtitle: "查看所选记录"
        },
        col: {caption: "选择列", bSubmit: "确定", bCancel: "取消"},
        errors: {errcap: "错误", nourl: "没有设置url", norecords: "没有要处理的记录", model: "colNames 和 colModel 长度不等！"},
        formatter: {
            integer: {thousandsSeparator: ",", defaultValue: "0"},
            number: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: "0.00"},
            currency: {
                decimalSeparator: ".",
                thousandsSeparator: ",",
                decimalPlaces: 2,
                prefix: "",
                suffix: "",
                defaultValue: "0.00"
            },
            date: {
                dayNames: ["日", "一", "二", "三", "四", "五", "六", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",],
                monthNames: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                AmPm: ["am", "pm", "上午", "下午"],
                S: function (b) {
                    return b < 11 || b > 13 ? ["st", "nd", "rd", "th"][Math.min((b - 1) % 10, 3)] : "th"
                },
                srcformat: "Y-m-d",
                newformat: "Y-m-d",
                parseRe: /[#%\\\/:_;.,\t\s-]/,
                masks: {
                    ISO8601Long: "Y-m-d H:i:s",
                    ISO8601Short: "Y-m-d",
                    ShortDate: "n/j/Y",
                    LongDate: "l, F d, Y",
                    FullDateTime: "l, F d, Y g:i:s A",
                    MonthDay: "F d",
                    ShortTime: "g:i A",
                    LongTime: "g:i:s A",
                    SortableDateTime: "Y-m-d\\TH:i:s",
                    UniversalSortableDateTime: "Y-m-d H:i:sO",
                    YearMonth: "F, Y"
                },
                reformatAfterEdit: false,
                userLocalTime: false
            },
            baseLinkUrl: "",
            showAction: "",
            target: "",
            checkbox: {disabled: true},
            idName: "id"
        }
    })
})(jQuery);