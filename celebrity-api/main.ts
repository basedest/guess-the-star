import { DOMParser } from "jsr:@b-fuze/deno-dom";

async function fetchCelebrities() {
  const response = await fetch("https://today.yougov.com/ratings/entertainment/fame/people/all");
  const html = await response.text();
  const doc = new DOMParser().parseFromString(html, "text/html");
  
  const images = doc?.querySelectorAll(".rankings-entities-list-item img") || [];
  const names = doc?.querySelectorAll(".rankings-entities-list-item .entity-name") || [];
  
  const celebrities = [];
  for (let i = 0; i < 20; i++) {
    const imageUrl = images[i]?.getAttribute("src") || "";
    celebrities.push({
      image_url: imageUrl.replace("pw=70", "pw=500"),
      name: names[i]?.textContent?.trim() || ""
    });
  }
  
  return celebrities;
}

export default {
  async fetch(req) {
    try {
      const celebrities = await fetchCelebrities();
      return new Response(JSON.stringify(celebrities), {
        headers: {
          "content-type": "application/json",
          "access-control-allow-origin": "*"
        }
      });
    } catch (error: any) {
      return new Response(JSON.stringify({ error: error?.message }), {
        status: 500,
        headers: {
          "content-type": "application/json"
        }
      });
    }
  },
} satisfies Deno.ServeDefaultExport;
