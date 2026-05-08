import { useState } from "react";
import "./index.css";

function App() {
  const [title, setTitle] = useState("");
  const [books, setBooks] = useState([]);
  const [userName, setUserName] = useState("");
  const [expectedReturnDate, setExpectedReturnDate] = useState("");
  const [message, setMessage] = useState("");

  async function searchBooks() {
    setMessage("");

    const response = await fetch(`http://localhost:8080/books/search?title=${title}`);
    const data = await response.json();

    setBooks(data);
  }

  async function borrowBook(bookId) {
    setMessage("");

    if (!userName || !expectedReturnDate) {
      setMessage("Please enter user name and expected return date.");
      return;
    }

    const response = await fetch("http://localhost:8080/loans", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        bookId,
        userName,
        expectedReturnDate,
      }),
    });

    if (response.ok) {
      setMessage("Loan created successfully.");
      searchBooks();
    } else {
      const error = await response.json();
      setMessage(error.error || "Could not create loan.");
    }
  }

  return (
    <main className="app">
      <section className="hero">
        <h1>Library Search</h1>
        <p>Search for books and borrow them easily.</p>
      </section>

      <section className="card search-card">
        <input
          type="text"
          placeholder="Search book by title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <button onClick={searchBooks}>Search</button>
      </section>

      <section className="card">
        <h2>Borrow a book</h2>

        <div className="form-grid">
          <label>
            User name
            <input
              type="text"
              placeholder="User name"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
            />
          </label>

          <label>
            Expected return date
            <input
              type="date"
              value={expectedReturnDate}
              onChange={(e) => setExpectedReturnDate(e.target.value)}
            />
          </label>
        </div>

        <div className="book-list">
          {books.map((book) => (
            <div className="book-item" key={book.id}>
              <div>
                <h3>{book.title}</h3>
                <p>
                  Copies available:{" "}
                  <span className="badge">{book.availableCopies}</span>
                </p>
              </div>

              <button
                disabled={book.availableCopies <= 0}
                onClick={() => borrowBook(book.id)}
              >
                Borrow
              </button>
            </div>
          ))}
        </div>

        {message && <p className="message">{message}</p>}
      </section>
    </main>
  );
}

export default App;