/**
 * 
 */

function initializeHeaderEvent() {
    $("#searchForm").on("submit", function (e) {
		e.preventDefault();
		const keyword = $("#keyword").val().trim();
		if (keyword.length < 3) {
			alert("검색어는 3자 이상입니다.");
			return;
		}
		this.submit();
    });
}