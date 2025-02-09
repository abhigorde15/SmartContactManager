console.log("Contact.js")
const viewContactModal = document.getElementById("view_contact_modal");
const baseUrl = "http://localhost:8080"
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};
const modal = new Modal(viewContactModal,options,instanceOptions)
function openContactModal(){
	modal.show();
}
function closeContactModal(){
	modal.hide();
}
async function loadContacData(id){
	try{
		console.log(id);
		const data = await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
		
		document.querySelector('#contact_name').innerHTML=data.name;
		document.querySelector('#contact_email').innerHTML=data.email;
		document.querySelector('#contact_phone').innerHTML=data.phoneNumber;

		document.querySelector('#contact_img').src=data.picture;
	
	    document.querySelector('#contact_description').innerHTML=data.description;
		document.querySelector('#contact_instagram_link').innerHTML=data.instagram_link;
		document.querySelector('#contact_adress').innerHTML=data.adress;
		if(data.favorite == true){
			document.querySelector('#contact_favourite').innerHTML="It's Favourite Contact";
		}
		
		
		openContactModal();
	}
	catch(err){
		console.log(err);
	}
	
}
async function deleteContact(id){
	Swal.fire({
	  title: "Do you want to Delete the Contact?",
	  showDenyButton: false,
	  showCancelButton: true,
	  confirmButtonText: "Delete",
	  denyButtonText: `Don't save`
	}).then((result) => {
	  /* Read more about isConfirmed, isDenied below */
	  if (result.isConfirmed) {
	    const url = `${baseUrl}/user/contacts/delete/`+id;
	    window.location.replace(url);
	 } else if (result.isDenied) {
	    Swal.fire("Changes are not saved", "", "info");
	  }
	});
}