import "../styles/globals.css"
import type { Metadata } from "next"

export const metadata: Metadata = {
  title:"Hello tailwind App",
  description: "Next.js vơi tailwinds"
}
export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="vi">
      <body className="bg-gray-100 min-h-screen p-8 font-sans">
        <div className="max-w-xl mx-auto bg-white shadow rounded p-6">
          {children}
        </div>
      </body>
    </html>
  )
}