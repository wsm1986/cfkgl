$(document).ready( function () {
	 var table = $('#employeesTable').DataTable({
			"sAjaxSource": "/employees",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "id"},
		          { "mData": "name" },
				  { "mData": "lastName" },
				  { "mData": "email" },
				  { "mData": "phone" },
				  { "mData": "active" }
			]
	 })
});


$(document).ready(function() {
    $('#employeesTable12').DataTable( {
    	 "footerCallback": function ( row, data, start, end, display ) {
	            var api = this.api(), data;
	 
	            // Remove the formatting to get integer data for summation
	            var intVal = function ( i ) {
	                return typeof i === 'string' ?
	                    i.replace(/[\$,]/g, '')*1 :
	                    typeof i === 'number' ?
	                        i : 0;
	            };
	 
	            // Total over all pages
	            total = api
	                .column( 6 )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	            // Total over this page
	            // Total over this page
	            pageTotal = api
	                .column(6 , { page: 'current'} )
	                .data()
	                .reduce( function (a, b) {
	                    return intVal(a) + intVal(b);
	                }, 0 );
	            
	            console.log(total);
	            console.log(pageTotal);
	            $( api.column( 6 ).footer() ).html(
	                '$'+pageTotal +' ( $'+ total +' total)'
	            );
	        }
			});
        }
    } );
} );
    
    
$(document).ready( function () {
	 var table = $('#operadorasTable').DataTable({
			"sAjaxSource": "/operadoraIni?page=0&size=100",
			"sAjaxDataProp": "_embedded.operadoraIni",
			"pagingType": "full_numbers",
			"bProcessing": true,
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "id"},
		          { "mData": "nome" },
				  { "mData": "cnpj" },
		          {
					  "data": 'id',
			                "render": function ( data, type, row ) {
			                	  return '<a href="remover/'+data+'">Download</a>';
			                },
			                "targets": 0
			            
		          }
			]
	 
	 })
	 
			 
	 
	 $('#operadorasTable tbody').on( 'click', 'button', function () {
		 return "<a href=${(#mvc.url('OC#remover').arg(0,operadora)).build()}";
		} );
});

