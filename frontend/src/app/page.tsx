import HelloMessage from "@/components/HelloMessage"

export default function HomePage() {
  return (
    <><div className="space-y-4">
      <h1 className="text-2xl font-bold"> Trang chính</h1>
      <HelloMessage name="Hello world"></HelloMessage>
    </div><h1>Hello, Next.js!</h1></>
    );
}