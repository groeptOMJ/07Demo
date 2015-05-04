var target;
$(function() {
	//make divs draggable
	$( "#dragcho1" ).draggable();
	$( "#dragcho2" ).draggable();
	//make div droppable
	$( "#dropbox" ).droppable({
	      drop: function( event, ui ) {
	    	  //read special attribute data-id from the div
	    	  var dest = $(ui.helper).data("id");
	    	  
	    	  //check result
	    	  if(dest == target){
	    		  
	    		  //color the dropzone and write some text
		    	  $( this )
		          .addClass( "ui-state-highlight" )
		          .find( "p" )
		            .html( "Dropped ok! " );
		    	  
		    	  //wait 1000 ms before calling addScore
		    	  setTimeout(function(){
		    		  addScore()
		    		}, 1000);
		    	  //wait 2000 ms before calling reload on window
		    	  setTimeout(function(){
		    		  window.location.reload()
		    		}, 2000);
	    	  }
	    	  
	      }
	        
	    });
	generateQuestion();
});

//generate a question and remember the expected result
function generateQuestion(){
	//generate random nr from 1 - 10
	var nr = Math.floor((Math.random() * 10)); 
	if(nr > 5){
		$('#question').html("<p>Drag option 1 to the box</p>");
		target = 'choice1';
	}
	else{
		$('#question').html("<p>Drag option 2 to the box</p>");
		target = 'choice2';
	}
}

//perform a call to the servlet to keep the score
function addScore(){
	$.ajax({ 
		url: 'cics',
		type : "POST",
		async : false,
        data : {'usr':$('#usr').val(),'scr':'1'},
        dataType : "json",
        success : setScore,
        error : function (xhr, ajaxOptions, thrownError){  
            alert (thrownError);
        } 
	});
}

function setScore(score){
	alert($('#usr').val() + " New score: " + score.score);
}
