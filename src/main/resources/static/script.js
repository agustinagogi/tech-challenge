async function searchBooks() {

    const title =
        document.getElementById("titleInput").value;

    const response =
        await fetch(`/books/search?title=${title}`);

    const books = await response.json();

    const results =
        document.getElementById("results");

    results.innerHTML = "";

    books.forEach(book => {
        const li = document.createElement("li");

        li.textContent =
            `${book.title} - Copies available: ${book.availableCopies}`;

        results.appendChild(li);
    });

}