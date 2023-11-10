using Microsoft.AspNetCore.Mvc;

namespace App.WebApi.Controllers
{
    [ApiController]
    [Route("healthcheck")]
    //[Route("api/[controller]")]
    public class HealthCheckController : ControllerBase
    {
        [HttpGet]
        public IActionResult CheckHealth()
        {
            // Implement your health check logic here
            // Return 200 OK if the service is healthy, and another status code if not
            return Ok();
        }
    }
}
