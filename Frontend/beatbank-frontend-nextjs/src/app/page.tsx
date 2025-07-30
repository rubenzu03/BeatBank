
"use client";

import React from "react";
import Link from "next/link";

export default function Home() {
  return (
    <main className="landing-main">
      <section className="landing-section">
        <img src="/logo.svg" alt="BeatBank Logo" className="landing-logo" />
        <h1 className="landing-title">Welcome to BeatBank</h1>
        <p className="landing-description">
          Manage songs, artists, albums and genres in a simple way.
        </p>
        <div className="landing-buttons">
          <Link href="/allSongs" className="landing-btn landing-btn-explorar">
            All songs
          </Link>
          <Link href="/search" className="landing-btn landing-btn-login">
            Search
          </Link>
          <Link href="/explore" className="landing-btn landing-btn-register">
            Explore
          </Link>
        </div>
        <div className="landing-info">
          <h2>
            {/* In BeatBank, you can manage your music library, and discover new songs. */}
          </h2>
        </div>
      </section>
    </main>
  );
}
