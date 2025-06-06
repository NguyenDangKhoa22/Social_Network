// components/HelloMessage.txs
"use client";

type Props = {
    name: String;
}

export default function HelloMessage({name}: Props){
    return (
      <p className="text-xl text-white bg-blue-500 px-4 py-2 rounded shadow-md">
      Hello {name} 👋
    </p>
    );
}