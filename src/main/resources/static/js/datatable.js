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
			"sAjaxSource": "/operadora",
			"sAjaxDataProp": "_embedded.operadora",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "id"},
		          { "mData": "nome" },
				  { "mData": "cnpj" },
		          {
					  "data": 'id',
			                // The `data` parameter refers to the data for the cell (defined by the
			                // `data` option, which defaults to the column being worked with, in
			                // this case `data: 0`.
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

