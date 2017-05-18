// note that months are zero-based in the JavaScript Date object, so month 3 is April
var items = new vis.DataSet([]);

var min = new Date(2015, 10, 1); // 1 april
var max = new Date(2015, 11, 30); // 30 april

var container = document.getElementById('visualization');
var options = {
	editable : true,

	onAdd : function(item, callback) {
		swal({
			title : "What do you want to add?",
			text : "Add Income or Expense",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "Expense",
			cancelButtonText : "Income",
		}, function(isConfirm) {
			if (isConfirm) {
				$('#feedExpensesModal').modal('show');
				$('#feedExpensesModal').find('.time').val(item.start);
			} else {
				$('#feedAssetsModal').modal('show');
				$('#feedAssetsModal').find('.time').val(item.start);
			}
		});
	},

	onMove : function(item, callback) {
		var title = 'Do you really want to move the item to\n' + 'start: '
				+ item.start + '\n' + 'end: ' + item.end + '?';

		prettyConfirm('Move item', title, function(ok) {
			if (ok) {
				callback(item); // send back item as confirmation (can be
								// changed)
				var data = {id : item.id, start : item.start};
				var posting = $.post("./rs/feed/moveTimelineItem", data);
				posting.done(function(msg) {
					if(msg == 'success') {
						displayAlert('Your Timeline change is saved to your records');
					}
				});
			} else {
				callback(null); // cancel editing item
			}
		});
	},

	onMoving : function(item, callback) {
//		if (item.start < min)
//			item.start = min;
//		if (item.start > max)
//			item.start = max;
//		if (item.end > max)
//			item.end = max;

		callback(item); // send back the (possibly) changed item
	},

	onUpdate : function(item, callback) {
		var data = {id : item.id};
		$.getJSON('./rs/feed/getMoneyDataById', data , function(result) {
			amount = result;
			prettyPrompt('Update item', 'Enter new amount:', amount, function(
					value) {
				if (value) {
					callback(item); // send back adjusted item
					var data = {id : item.id, amount : value};
					var posting = $.post("./rs/feed/updateTimelineItem", data);
					posting.done(function(msg) {
						if(msg == 'success') {
							displayAlert('Your Timeline change is saved to your records');
						}
					});
				} else {
					callback(null); // cancel updating the item
				}
			});
		}).done(function() {
		}).fail(function() {
		}).always(function() {
		});
	},

	onRemove : function(item, callback) {
		prettyConfirm('Remove item', 'Do you really want to remove item '
				+ item.content + '?', function(ok) {
			if (ok) {
				callback(item); // confirm deletion
				var data = {id : item.id};
				var posting = $.post("./rs/feed/deleteTimelineItem", data);
				posting.done(function(msg) {
					if(msg == 'success') {
						displayAlert('Your Timeline item has been removed from your records');
					}
				});
			} else {
				callback(null); // cancel deletion
			}
		});
	}
};
var timeline = new vis.Timeline(container, items, options);

items.on('*', function(event, properties) {
	logEvent(event, properties);
});

function logEvent(event, properties) {
	var log = document.getElementById('log');
	var msg = document.createElement('div');
	msg.innerHTML = 'event=' + JSON.stringify(event) + ', ' + 'properties='
			+ JSON.stringify(properties);
	log.firstChild ? log.insertBefore(msg, log.firstChild) : log
			.appendChild(msg);
}

function prettyConfirm(title, text, callback) {
	swal({
		title : title,
		text : text,
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : "#DD6B55"
	}, callback);
}

function prettyPrompt(title, text, inputValue, callback) {
	swal({
		title : title,
		text : text,
		type : 'input',
		showCancelButton : true,
		inputValue : inputValue,
	}, callback);
}