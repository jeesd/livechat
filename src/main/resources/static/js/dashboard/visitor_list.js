/**
 * Created by lubo08 on 2.10.2014.
 */
define(['jquery','bootstrap','datatables'], function($){
//$( document ).ajaxComplete(function( event,request, settings ) {
    var initialize = function() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        if (!$.fn.dataTable.isDataTable('#dataTable')) {
            $('#dataTable').DataTable({
                "processing": true,
                "serverSide": true,
                "jQueryUI": true,
                "autoWidth": false,
                "deferRender": true,
                "pagingType": "full_numbers",
                "columns": [
                    { "data": "status",
                        "render": function (data, type, full, meta) {
                            if (data == 'ONLINE') {
                                return '<span class="label label-success">'+data+'</span>';
                            } else {
                                return '<span class="label label-danger">'+data+'</span>';
                            }
                        }  },
                    { "data": "ip" },
                    { "data": "browser" },
                    { "data": "system" },
                    { "data": "countryCode" },
                    { "data": "countryName" }
                ],
                "ajax": {
                    dataType: 'json',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: 'POST',
                    beforeSend: function (request) {
                        request.setRequestHeader(header, token);
                    },
                    error: function (xhr) {
                        ErrorMessage.showError(xhr.responseJSON);
                    },
                    data: function (d) {
                        return JSON.stringify(d);
                    },
                    "url": "/visitor-list-table"
                },
                "initComplete": function (settings, json) {
                    $('#dataTablelabel').html(json.recordsTotal + ' Items');
                    //$('#visitorsCount').html(json.recordsTotal);
                }
            });
        }

        var ErrorMessage = {
            showError: function (responseJSON) {
                $('#lightCustomModal H4').html(responseJSON.errorMessage);
                $('#lightCustomModal a').html(responseJSON.buttonText);
                $('#lightCustomModal a').attr("href", responseJSON.forwardUrl);
                $('#lightCustomModal').popup('show');
            }
        };
    };
//});

    return {
        initialize: initialize
    };
});