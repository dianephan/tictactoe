import Game from "./Game"; 
import GameCreation from './GameCreation.js';

import React, { useState } from 'react';

function Page() { 

    const [gameId, setGameId] = useState(null);
    
    function ChangePage (gameId) { 
      if (gameId) { 
        // handle 404 if not found 
        return <Game gameId={gameId}/>
      }
        return <GameCreation setGameId={setGameId}/>
      }

      return ChangePage(gameId);
}

export default Page; 