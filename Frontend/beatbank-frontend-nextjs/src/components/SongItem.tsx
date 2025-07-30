
export interface Artist {
  id: number;
  name: string;
}

export interface Song {
  id: number;
  name: string;
  artists: Artist[] | string;
}

interface SongItemProps {
  song: Song;
}

const SongItem = ({ song }: SongItemProps) => {
  return (
    <li className="p-3 bg-gray-100 rounded shadow-sm">
      <span className="font-semibold">{song.name}</span> by {Array.isArray(song.artists)
        ? song.artists.map((artist) => (typeof artist === "string" ? artist : artist.name)).join(", ")
        : song.artists}
    </li>
  );
};

export default SongItem;
