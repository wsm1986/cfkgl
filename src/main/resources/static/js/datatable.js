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

