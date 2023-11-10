using System.Collections.Generic;
using App.ApplicationCore.Domain;
using App.WebApi.Settings;
using internship_service.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Driver.Core.Configuration;

namespace internship_service.Controllers
{
    [Route("biochar/internship-service/Internship")]
    [ApiController]
    public class InternshipController : ControllerBase
    {
        private readonly IMongoCollection<Internship> internshipCollection; 
        public InternshipController(IOptions<MongoDbSettings> mongoDbSettings)
        { 
            var connectionstring = $"mongodb://{mongoDbSettings.Value.Host}:{mongoDbSettings.Value.Port}/{mongoDbSettings.Value.DatabaseName}";
            var mongoUrl = MongoUrl.Create(connectionstring);
            var mongoClient = new MongoClient(mongoUrl);
            var database = mongoClient.GetDatabase(mongoUrl.DatabaseName);
            internshipCollection = database.GetCollection<Internship>("internship");
        }

        [HttpGet]//[Route("selectAll")]
        public async Task<ActionResult<IEnumerable<Internship>>> selectAll()
        {
            var internships = await internshipCollection.Find(Builders<Internship>.Filter.Empty).ToListAsync();
            if (internships == null || !internships.Any())
            {
                return Ok(new List<Internship>()); // Return an empty list as a 200 OK response.
            }
            return Ok(internships); // Return a 200 OK response with the internship data.

        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Internship>> GetById(string id)
        {
            var filterDefinition = Builders<Internship>.Filter.Eq(x => x.Id, id);
            var internship = await internshipCollection.Find(filterDefinition).SingleOrDefaultAsync();
            if (internship == null)
            {
                return NotFound(); // Return a 404 Not Found response.
            }
            return Ok(internship); // Return a 200 OK response with the internship data.
        }

        [HttpGet("username/{username}")]
        public async Task<ActionResult<IEnumerable<Internship>>>  GetByUsername(string username)
        {
            var filterDefinition = Builders<Internship>.Filter.Eq(x => x.Username, username);
            var internship = await internshipCollection.Find(filterDefinition).ToListAsync();
            if (internship == null)
            {
                return NotFound(); // Return a 404 Not Found response.
            }
            return Ok(internship); // Return a 200 OK response with the internship data.
        }

        [HttpPost]
        public async Task<IActionResult> insert([FromBody] Internship newInternship)
        {
            newInternship.DateCreation = DateTime.Now;
            newInternship.Status = Status.ONHOLD;
            try
            {
                  await internshipCollection.InsertOneAsync(newInternship);
                return Ok(newInternship); // Return a 200 OK response for a successful insertion.
            }
            catch (Exception ex)
            {
                return StatusCode(500, "An error occurred: " + ex.Message);
            }
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> Update(string id, [FromBody] Internship updatedInternship)
        {
            if (string.IsNullOrEmpty(id))
            {
                return BadRequest("ID cannot be null or empty.");
            }
            var filterDefinition = Builders<Internship>.Filter.Eq(x => x.Id, id);
            var updateDefinition = Builders<Internship>.Update
                .Set(x => x.Nom, updatedInternship.Nom)
                .Set(x => x.Prenom, updatedInternship.Prenom)
                .Set(x => x.Subject, updatedInternship.Subject)
                .Set(x => x.University, updatedInternship.University)
                .Set(x => x.PhoneUniversity, updatedInternship.PhoneUniversity)
                .Set(x => x.Studylevel, updatedInternship.Studylevel);
            var result = await internshipCollection.UpdateOneAsync(filterDefinition, updateDefinition);

            if (result.MatchedCount == 0)
            {
                return NotFound(); // Return a 404 Not Found response.
            }// If you want to return the updated document, you can query it again
            var updatedDocument = await internshipCollection.Find(filterDefinition).FirstOrDefaultAsync();
            return Ok(updatedDocument); // Return a 200 OK response with the updated internship data.
        }
        [HttpPut("{id}/{status}")]
        public async Task<ActionResult> Update(string id, Status status  )
        {
            if (string.IsNullOrEmpty(id) )
            {
                return BadRequest("ID cannot be null or empty.");
            }
            if (status == null)
            {
                return BadRequest("Status cannot be null or empty.");
            }
            var filterDefinition = Builders<Internship>.Filter.Eq(x => x.Id, id);
            var updateDefinition = Builders<Internship>.Update
                .Set(x => x.Status, status );
            var result = await internshipCollection.UpdateOneAsync(filterDefinition, updateDefinition);

            if (result.MatchedCount == 0)
            {
                return NotFound(); // Return a 404 Not Found response.
            }// If you want to return the updated document, you can query it again
            var updatedDocument = await internshipCollection.Find(filterDefinition).FirstOrDefaultAsync();
            return Ok(updatedDocument); // Return a 200 OK response with the updated internship data.
        }
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(string id)
        {
            try
            {
                var filterDefinition = Builders<Internship>.Filter.Eq(x => x.Id, id);
                var result = await internshipCollection.DeleteOneAsync(filterDefinition);
                if (result.DeletedCount == 0)
                {
                    return NotFound();  // If no document was deleted, return a "Not Found" (HTTP 404) response.
                }
                return Ok(); // Return a 200 OK response for a successful deletion.
            }
            catch (Exception ex)
            {
                return StatusCode(500, "An error occurred: " + ex.Message);
            }
        }
    }
}

/*var dbHost = "127.0.0.1";
var dbName = "internshipDB"; 
var connectionstring = $"mongodb://{dbHost}:27017/{dbName}";
var mongoUrl = MongoUrl.Create(connectionstring);
var mongoClient = new MongoClient(mongoUrl);
var database = mongoClient.GetDatabase(mongoUrl.DatabaseName);
_internshipCollection = database.GetCollection<Internship>("internship");*/ 